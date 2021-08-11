package security;


import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.faces.application.FacesMessage;


public class SecurityManager {
    private static final Logger log = Logger.getLogger(SecurityManager.class);
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);

    /**
     * Process to login boolean.
     *
     * @param username   the username
     * @param password   the password
     * @param rememberMe the remember me
     *
     * @return the boolean
     */
    public static boolean processToLogin(String username, String password, boolean rememberMe) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);

        try {
            subject.login(token);

            return true;

        } catch (UnknownAccountException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "msg.wrongCredentials", "msg.wrongCredentials.detail");
        } catch (IncorrectCredentialsException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "msg.wrongCredentials", "msg.wrongCredentials.detail");
        } catch (LockedAccountException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "Error", "Contact admin");
        } catch (ExcessiveAttemptsException ex) {
            log.error(ex.getMessage(), ex);
            message.display(FacesMessage.SEVERITY_ERROR, "Unknown error", "Contact administrator");
        } finally {
            token.clear();
        }

        return false;
    }

    /**
     * Process to logout boolean.
     *
     * @return the boolean
     */
    public static boolean processToLogout() {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isAuthenticated()) {
            subject.getSession().stop();
            return true;
        }

        return false;
    }

    /**
     * User is logged boolean.
     *
     * @return the boolean
     */
    public static boolean userIsLogged() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    /**
     * User is remembered string.
     *
     * @return the string
     */
    public static String userIsRemembered() {
        Subject subject = SecurityUtils.getSubject();

        if (subject.isRemembered())
            return (String) subject.getPrincipal();

        return null;
    }

    /**
     * Gets session attribute.
     *
     * @param name the name
     *
     * @return the session attribute
     */
    public static Object getSessionAttribute(String name) {
        Session session = SecurityUtils.getSubject().getSession();

        Object attribute = session.getAttribute(name);

        if (attribute == null)
            log.debug("No such attribute named : " + name + "find in session : " + session.getId());
        else
            log.debug(name + " retrieve from session : " + session.getId());

        return attribute;
    }

    /**
     * Save attribute in session.
     *
     * @param name  the name
     * @param value the value
     */
    public static void saveAttributeInSession(String name, Object value) {
        Session session = SecurityUtils.getSubject().getSession();

        session.setAttribute(name, value);

        log.debug("Attribute " + name + " with value = " + value + " save in session : " + session.getId() + " with time-out: " + session.getTimeout());
    }

    /**
     * Encrypt password string.
     *
     * @param password the password
     *
     * @return the string
     */
    public static String encryptPassword(String password) {
        PasswordMatcher matcher = new PasswordMatcher();

        return matcher.getPasswordService().encryptPassword(password);
    }
}