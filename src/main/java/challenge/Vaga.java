package challenge;

import exceptions.EstacionamentoException;
import exceptions.VagaException;

public class Vaga {

    private Carro carro;

    public Vaga(Carro carro){
        this.carro = carro;
    }

    public Vaga(){}

    public Carro getCarro() {
        return carro;
    }

    public boolean isEmpty(){
        return carro == null;
    }

    public void estacionar(Carro carro) throws EstacionamentoException {
        if(isEmpty())
            this.carro = carro;
        else
            throw new VagaException("Vaga Oculpada");
    }

    public Carro sairDaVaga(){
        Carro aux;
        if(isEmpty()){
            throw new VagaException("Vaga Vazia!");
        }
        aux = this.carro;
        this.carro = null;
        return aux;
    }

    public boolean isThisCar(Carro carro){
        return this.carro.equals(carro);
    }

}
