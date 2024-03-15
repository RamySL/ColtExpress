package modele;

import java.util.ArrayList;

public class Train {
    int NB_WAGONS;
    ComposanteTrain[] composantes;
    Bandit bandit;
}

abstract class ComposanteTrain {
    int position; //position dans le train;
}

class Wagon extends ComposanteTrain{
    ArrayList<Buttin> buttins;
}

class Locomotive extends ComposanteTrain{
    Magot magot;
}
