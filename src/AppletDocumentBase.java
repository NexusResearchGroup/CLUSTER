
 
import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class AppletDocumentBase extends Applet {
   private URL logo;

    @Override
   public void init() {
        //

       try {
		getAppletContext().showDocument
		  (new URL(getCodeBase()+"www.google.com"),"_blank");
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} }
 
}