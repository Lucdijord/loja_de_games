package controller.casos_de_uso;

import model.Jogo;
import model.Produto;
import java.util.List;

public class DescontoProgressivo {

    public double calcularDescontoTotal(List<Produto> produtos, List<Integer> quantidades, List<Boolean> usados, double subtotal) {
        int totalItens = 0;
        double descontoJogosUsados = 0.0;

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            int quantidade = quantidades.get(i);
            boolean usado = usados.get(i);

            totalItens += quantidade;
            double precoBase = produto.getPreco() * quantidade;

            if (produto instanceof Jogo && usado) {
                descontoJogosUsados += precoBase * 0.05;
            }
        }

        double descontoVolume = 0.0;
        if (totalItens >= 5) {
            descontoVolume = subtotal * 0.12;
        } else if (totalItens >= 3) {
            descontoVolume = subtotal * 0.05;
        }

        return descontoJogosUsados + descontoVolume;
    }
}