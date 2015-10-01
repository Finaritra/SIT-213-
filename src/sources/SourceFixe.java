package sources;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class SourceFixe extends Source<Boolean> {


	
public SourceFixe(){
	super();
}

public SourceFixe(Information<Boolean> infoFixe){
	destinationsConnectees = new LinkedList <DestinationInterface <Boolean>> ();
    informationGeneree = infoFixe;
    informationEmise = null; 
}

 
   
}
