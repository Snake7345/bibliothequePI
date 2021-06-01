package pdfTools;

import entities.Factures;
import entities.FacturesDetail;
import entities.TarifsPenalites;
import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.servlet.annotation.WebServlet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;


@WebServlet("/ModelFactBiblioPena")


public class ModelFactBiblioPena implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ModelFactBiblioPena.class);

	/*Creation de la facture en PDF*/
	public void creation (Factures fact, ArrayList<TarifsPenalites> tp, FacturesDetail retard)  {
		try{
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String userdir = System.getProperty("user.dir");
		userdir = userdir.substring(0,userdir.length()-24);
		String image = userdir + "src\\main\\webapp\\Images\\biblioLib.png";
		ArrayList<String> price = new ArrayList<>();
		log.debug(image);
		Calendar cal = Calendar.getInstance();
		//Date date = cal.getTime();
		//variable a mettre dans la facture
		String numfacture = fact.getNumeroFacture();
		String utilisateur = fact.getUtilisateurs().getNumMembre();
		String nompreClient = fact.getUtilisateurs().getNom() + " " + fact.getUtilisateurs().getPrenom();
		String adresse="";
		/*Mettre une variable pour le code barre du livre*/
		for(UtilisateursAdresses ua : fact.getUtilisateurs().getUtilisateursAdresses())
		{
			if(ua.isActif())
			{
				adresse = ua.getAdresse().getRue() + " " + ua.getAdresse().getNumero() + " " + ua.getAdresse().getBoite() + " " + ua.getAdresse().getLocalites().getCp() + " " + ua.getAdresse().getLocalites().getVille();
			}
		}
		String laDateDuJour = sf.format(new java.util.Date());
		
		//calcule main d'oeuvre
		Double PHTVA = (fact.getPrixTvac()/1.21);
		Double TVA = fact.getPrixTvac() - (fact.getPrixTvac()/1.21);
		Double PTVAC = fact.getPrixTvac();

		//String total4 = String.format("%5.02f €", TVA);
		
		//Creer le pdf
		PDDocument doc = new PDDocument();
		
		// 1 page dans le PDF
		PDPage page = new PDPage();
		doc.addPage(page);
			  
	    //Creating the PDDocumentInformation object 
	    PDDocumentInformation pdd = doc.getDocumentInformation();    
	    pdd.setAuthor("BiblioLib");										//Setting the author of the document
	    pdd.setTitle("Facture"+numfacture); 							// Setting the title of the document
	    pdd.setSubject("Facturation du client: " + utilisateur); 	//Setting the subject of the document
	    pdd.setCreationDate(cal); 	    						//Setting the created date of the document 

	    /*(A4)
	     * Dimension:  mm = pt * 25.4 / 72  --> pt = mm*72/25.4
	     * marge 2 cm = 7.06
	     */

	    //PDPage page = doc.getPage(0);
	    PDImageXObject pdImage = PDImageXObject.createFromFile(image, doc);
	    PDPageContentStream contentStream = new PDPageContentStream(doc, page);
	    contentStream.drawImage(pdImage, 35, 650);
	    
	    // afficher une ligne 
	    
	    
	    contentStream.beginText(); 	   														//Begin the Content stream 
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);	//Setting the font to the Content stream  
	    contentStream.setNonStrokingColor(Color.GRAY);
	    contentStream.setLeading(24.5f);
	    
	    //Cr�ation de l'entete de la page
	    contentStream.newLineAtOffset(198, 725);	    							//Setting the position for the line (l x h)
	    String entete1 = "BiblioLib";
	    String entete2 = "Rue du Fort, 29 - 6000  CHARLEROI";
	    String entete3 = "TVA: BE0448.150.750 - Tel: 071 35 44 71 - Email: info@bibliolib.be";
	    contentStream.showText(entete1);      	    								//Adding text in the form of string 
	    contentStream.newLine();
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
	    contentStream.setLeading(14.5f);
	    contentStream.showText(entete2);
	    contentStream.newLine();
	    contentStream.showText(entete3);
	    contentStream.endText();
	    
	    // ligne d'ent�te
	    contentStream.setLineWidth(2);
		contentStream.moveTo(35, 670);
		contentStream.lineTo(550, 670);
		contentStream.closeAndStroke();
	    
	    Encadrement.creation(contentStream, 350,615,200,80);
	    
	  //Cr�ation de l'etiquette du client
	    PDFont font = PDType1Font.TIMES_ROMAN;
	    contentStream.beginText();
		contentStream.setFont(font, 14);
		contentStream.setNonStrokingColor(Color.BLACK);
		contentStream.setLeading(14.5f);
	    contentStream.newLineAtOffset(360, 600);
	    contentStream.showText("Client :");
	    contentStream.newLine();
	    contentStream.showText(nompreClient);
	    contentStream.newLine();
	    contentStream.showText(adresse);  
	    contentStream.newLine();
	    contentStream.endText();
	    

		
	    //entete facture
	    contentStream.beginText();
	    contentStream.setFont(PDType1Font.TIMES_BOLD,12);
	    contentStream.setLeading(14.5f);
	    contentStream.newLineAtOffset(80, 600);	 
	    String entetef1 = "Facture de penalite num : " + numfacture + " creee le " + laDateDuJour;
	   
	    contentStream.showText(entetef1);
	    contentStream.newLine();
	    contentStream.setFont(PDType1Font.COURIER_BOLD_OBLIQUE,10);
	    contentStream.endText();
	    
		//ligne verticale
		contentStream.setLineWidth(1);
		contentStream.moveTo(450, 475);
		contentStream.lineTo(450, 175);
		contentStream.closeAndStroke();
		
	    //cadre
	    
	    //travaux effectues
	    contentStream.beginText();
	    contentStream.newLineAtOffset(80, 455);	
	    contentStream.showText("Penalites : ");
	    contentStream.newLine();
	    contentStream.newLine();
		log.debug(fact.getFactureDetails().size());
	    for (TarifsPenalites TP: tp)
		{
	    	contentStream.showText(TP.getPenalite().getDenomination());
	    	contentStream.newLine();
	    	if(!TP.getPenalite().getDenomination().equals("Retard"))
			{
				price.add(String.valueOf(TP.getPrix()));
			}
	    	else if(retard != null){
				price.add(String.valueOf(retard.getPrix()));
			}
			contentStream.newLine();
		}
	    contentStream.endText();
	    
	    contentStream.beginText();
	    contentStream.newLineAtOffset(475, 455);	
	    contentStream.showText("Prix");
		contentStream.newLine();
		contentStream.newLine();
		for (String s : price) {
			contentStream.showText(s);
			contentStream.newLine();
			contentStream.newLine();
		}
		contentStream.endText();
	    // dessiner le cadre
	    
	    //decompte
	    contentStream.setNonStrokingColor(Color.BLACK);
	    contentStream.addRect(57, 260, 500, 2);
	    contentStream.fill();
	    
	    contentStream.beginText();
	    contentStream.setLeading(17.5f);
	    contentStream.newLineAtOffset(363, 235);
	    String total2 = "Prix HTVA";
	    String total3 = "TVA";
	    String total4 = "Total a payer";
	    contentStream.showText(total2);
	    contentStream.newLine();
	    contentStream.showText(total3);
	    contentStream.newLine();
	    contentStream.showText(total4);
	    contentStream.newLine();
	    contentStream.endText();
	    	    
	    contentStream.beginText();
	    contentStream.setLeading(17.5f);
	    contentStream.newLineAtOffset(475, 235);
	    String total5 = String.format("%5.02f Euros", PHTVA);
	    String total6 = String.format("%5.02f Euros", TVA);
	    String total7 = String.format("%5.02f Euros", PTVAC);
	    contentStream.showText(total5);
	    contentStream.newLine();
	    contentStream.showText(total6);
	    contentStream.newLine();
	    contentStream.showText(total7);
	    contentStream.endText();

	    //pied de page
	    contentStream.setNonStrokingColor(Color.RED);
	    contentStream.addRect(57, 100, 500, 2);
	    contentStream.fill();
	    
	    contentStream.beginText();
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
	    contentStream.setLeading(7.25f);
	    contentStream.newLineAtOffset(57, 90);	 
	    String pdp1 = "Conditions generales";
	    String pdp2 = "Toutes nos factures doivent etre paye au moment de la creation de la facture.";
	    String pdp4 = "Les reclamations doivent etre introduites par lettre recommandee, sous peine de decheance, dans les 8 jours de la reception de la facture.";
	    String pdp6 = "A defaut, nos factures sont reputees conformes.";
	    

	    contentStream.showText(pdp1);
	    contentStream.setFont(PDType1Font.HELVETICA, 7);
	    contentStream.newLine();
	    contentStream.newLine();
	    contentStream.showText(pdp2);
	    contentStream.newLine();
	    contentStream.showText(pdp4);
	    contentStream.newLine();
	    contentStream.showText(pdp6);
	    contentStream.endText();

	    
	    contentStream.close();

	    String path = userdir + "\\src\\main\\webapp\\Factures\\";
	    File file = new File(path);
	    if(file.mkdir()) 
	    {
	    	log.debug("Le dossier a bien été cree");
	    }
	    doc.save(path + numfacture + ".pdf");

	    doc.close();}
		catch (IOException e){
			log.debug(e.getMessage());
		}
	}
	
	
	//-------------------------------------------------------------------------------------------------------

	public static Logger getLog() {
		return log;
	}


}

