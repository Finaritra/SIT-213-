package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurParfait extends Transmetteur<Boolean,Boolean> {

	   
	
	/** 
	   * un constructeur factorisant les initialisations communes aux réalisations de la classe abstraite Transmetteur 
	   */
	       public TransmetteurParfait() {
	        super();
	      }

	     

	       /**
	         * ï¿½met l'information construite par le transmetteur  
	          */
	           public void emettre() throws InformationNonConforme
	           {
	        	   Information<Boolean> newInfo =modify( informationRecue);
	        	  
	        	// ï¿½mission vers les composants connectï¿½s  
	               for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
	                  destinationConnectee.recevoir(newInfo);
	               }
	               super.informationEmise = newInfo;
	                      
	            
	           }



		@Override
		public void recevoir(Information<Boolean> information) throws InformationNonConforme {
			// TODO Auto-generated method stub
			 super.informationRecue = information;   
			 
			 emettre();
		}
	    
		
		/**crée une nouvelle instance de Information
		 *@param information Information Ã  modifier (transmetteur parfait on modifie pas)
		 *@return l'information modifiÃ©
		*/
		public Information<Boolean> modify(Information<Boolean> information){
			Information<Boolean> info = new Information<Boolean>();
			for(int i=0;i<information.nbElements();i++)
			info.add(information.iemeElement(i));
			/*info.add(true);
      	    info.add(false);*/
			return info;
		}
	     
}
