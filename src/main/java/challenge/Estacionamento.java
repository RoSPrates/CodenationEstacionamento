package challenge;

import exceptions.EstacionamentoException;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {

    List<Vaga> vagas;
    final int LIMITE = 10;

    public Estacionamento() {
        vagas = new ArrayList<>();
    }

    public void estacionar(Carro carro) {
        validaMotorista(carro.getMotorista());
        if (vagas.size() < LIMITE) {
            vagas.add(new Vaga(carro));
        } else {
            for (Vaga v : vagas) {
                if(v.getCarro().getMotorista().getIdade() < 55){
                    v.sairDaVaga();
                    v.estacionar(carro);
                    return;
                }
            }

            throw new EstacionamentoException("Estacionamento Cheio");
        }
    }


    public int carrosEstacionados() {
        return vagas.size();
    }

    public boolean carroEstacionado(Carro carro) {
        List<Carro> carros = new ArrayList<>();
        for (Vaga v : vagas) {
            carros.add(v.getCarro());
        }
        return carros.contains(carro);
    }

    private void validaMotorista(Motorista motorista){
        if(motorista == null || motorista.getIdade() < 18 || motorista.getPontos() > 20)
            throw new EstacionamentoException("Motorista Ivalido!");
    }
}
