import challenge.*;
import exceptions.EstacionamentoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.util.concurrent.ThreadLocalRandom.current;

public class TestMain {

    Estacionamento estacionamento;

    @Before
    public void setUp() {
        estacionamento = new Estacionamento();
    }

    @Test(expected = NullPointerException.class)
    public void deveRetornarErroQuandoMotoristaNaoTemHabilitacao() {
        Motorista.builder().withNome("Ada")
                .withIdade(17)
                .withPontos(10)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void deveRetornarErroQuandoMotoristaNaoTemNome() {
        Motorista.builder()
                .withIdade(17)
                .withPontos(10)
                .withHabilitacao("12213")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRetorarErroQuandoIdadeNegativa() {
        Motorista.builder()
                .withIdade(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRetorarErroQuandoPontosNegativos() {
        Motorista.builder()
                .withPontos(-1);
    }


    @Test(expected = NullPointerException.class)
    public void deveRetornarErroQuandoNaoTemPlaca() {
        Carro.builder().withCor(Cor.COLORIDO).build();
    }

    @Test(expected = NullPointerException.class)
    public void deveRetornarErroQuandoNaoTemCor() {
        Carro.builder().withPlaca("234234").build();
    }


    @Test(expected = EstacionamentoException.class)
    public void naoDeveTerCarroAutonomo() {
        Carro carro = Carro.builder().withCor(Cor.COLORIDO).withPlaca("123").build();
        estacionamento.estacionar(carro);
    }

    @Test(expected = EstacionamentoException.class)
    public void naoDeveTerMotoristaDeMenor() {
        Motorista motorista = Motorista.builder().withNome("Ada")
                .withIdade(17)
                .withPontos(10)
                .withHabilitacao("1231")
                .build();
        Carro carro = Carro.builder().withCor(Cor.PRETO).withPlaca("123").withMotorista(motorista).build();
        estacionamento.estacionar(carro);
    }

    @Test(expected = EstacionamentoException.class)
    public void naoDeveEstacionarMotoristaSemPontos() {
        Motorista motorista = Motorista.builder().withNome("Ada")
                .withIdade(25)
                .withPontos(30)
                .withHabilitacao("1231")
                .build();
        Carro carro = Carro.builder().withCor(Cor.BRANCO).withPlaca("123").withMotorista(motorista).build();
        estacionamento.estacionar(carro);
    }

    @Test
    public void deveEstacionar() {
        Motorista motorista = Motorista.builder().withNome("Ada").withIdade(20)
                .withPontos(3)
                .withHabilitacao("1231")
                .build();
        Carro carro = Carro.builder().withCor(Cor.BRANCO).
                withPlaca("123")
                .withMotorista(motorista)
                .build();

        estacionamento.estacionar(carro);

        Assert.assertEquals(1, estacionamento.carrosEstacionados());

    }

    @Test
    public void naoDeveUltrapassarEstacionamento() {
        Motorista ada = Motorista.builder().withNome("Ada").withIdade(20)
                .withPontos(3)
                .withHabilitacao("1231")
                .build();

        Carro carroBranco = Carro.builder().withCor(Cor.BRANCO).
                withPlaca("123")
                .withMotorista(ada)
                .build();

        estacionamento.estacionar(carroBranco);

        for (int indice = 1; indice <= 10; indice++) {

            Motorista motorista = Motorista.builder()
                    .withNome("Motorista " + indice)
                    .withIdade(20)
                    .withPontos(3)
                    .withHabilitacao(Long.toString(current().nextLong()))
                    .build();

            Carro carro = Carro.builder().withCor(Cor.BRANCO).
                    withPlaca("123")
                    .withMotorista(motorista)
                    .build();

            estacionamento.estacionar(carro);
        }

        Assert.assertEquals(10, estacionamento.carrosEstacionados());
        Assert.assertFalse(estacionamento.carroEstacionado(carroBranco));
    }


    @Test
    public void casoPrimeiroMotoristaSejaSeniorEleNaoDeveSair() {

        Motorista ada = Motorista.builder().withNome("Ada")
                .withIdade(60)
                .withPontos(3)
                .withHabilitacao("1231")
                .build();

        Carro carroBranco = Carro.builder().withCor(Cor.BRANCO).
                withPlaca("123")
                .withMotorista(ada)
                .build();

        estacionamento.estacionar(carroBranco);

        for (int indice = 1; indice <= 10; indice++) {

            Motorista motorista = Motorista.builder()
                    .withNome("Motorista " + indice)
                    .withIdade(20)
                    .withPontos(3)
                    .withHabilitacao(Long.toString(current().nextLong()))
                    .build();

            Carro carro = Carro.builder()
                    .withCor(getCor()).
                            withPlaca("123")
                    .withMotorista(motorista)
                    .build();

            estacionamento.estacionar(carro);
        }

        Assert.assertEquals(10, estacionamento.carrosEstacionados());
        Assert.assertTrue(estacionamento.carroEstacionado(carroBranco));
    }


    @Test(expected = EstacionamentoException.class)
    public void casoTodosSejamSeniorONovoMotoristaNaoTeraVaga() {

        Motorista ada = Motorista.builder().withNome("Ada")
                .withIdade(60)
                .withPontos(3)
                .withHabilitacao("1231")
                .build();

        Carro carroBranco = Carro.builder().withCor(Cor.BRANCO).
                withPlaca("123")
                .withMotorista(ada)
                .build();

        estacionamento.estacionar(carroBranco);

        for (int indice = 1; indice <= 10; indice++) {

            Motorista motorista = Motorista.builder()
                    .withNome("Motorista " + indice)
                    .withIdade(60)
                    .withPontos(3)
                    .withHabilitacao(Long.toString(current().nextLong()))
                    .build();

            Carro carro = Carro.builder()
                    .withCor(getCor()).
                            withPlaca("123")
                    .withMotorista(motorista)
                    .build();

            estacionamento.estacionar(carro);
        }

    }

    private Cor getCor() {
        return Cor.values()[current().nextInt(0, 2)];
    }
}

