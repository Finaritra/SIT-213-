package sources;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class SourceAleatoire extends Source {

/** 
 *   un constructeur factorisant les initialisations communes aux réalisations de la classe abstraite Source 
 */	
public SourceAleatoire(){
	super();
}

/** 
 * constructeur de la classe SourceAleatoire 
 * @param infoFixe information à envoyer vers les destinations
 */
public SourceAleatoire(Information<Boolean> infoFixe){
	destinationsConnectees = new LinkedList <DestinationInterface <Boolean>> ();
    informationGeneree = infoFixe;
    informationEmise = null; 
}


}
