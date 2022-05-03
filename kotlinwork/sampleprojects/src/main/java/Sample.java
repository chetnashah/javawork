public class Sample {


}

class SquareCabin extends Dwelling {

    @Override
    void hola() {

    }
}

abstract class Dwelling {
    int buildingMaterial;
    int hasRoom(){
        return 0;
    }
    abstract void hola();
}