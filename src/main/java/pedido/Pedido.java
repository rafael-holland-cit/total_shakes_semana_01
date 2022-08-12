package pedido;

import ingredientes.Adicional;
import produto.Shake;

import java.util.*;

import static java.util.Map.entry;

public class Pedido{

    private int id;
    private  ArrayList<ItemPedido> itens;
    private Cliente cliente;


    public Pedido(int id, ArrayList<ItemPedido> itens,Cliente cliente){
        this.id = id;
        this.itens=itens;
        this.cliente=cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        if(itens == null){
            itens = new ArrayList<>();
        }
        return itens;
    }

    public int getId(){
        return this.id;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio){
        double total= 0;
        for (ItemPedido item: itens) {
            var shake = item.getShake();
            var qtdShake = item.getQuantidade();
            var adicionais = shake.getAdicionais();

            var precoBase = cardapio.getPrecos().get(shake.getBase());
            var precoBaseComTamanho = precoBase + (precoBase * shake.getTipoTamanho().multiplicador);
            var precoComQuantidade = precoBaseComTamanho;
            var totalAdicionais = adicionais.stream().map(adicional -> cardapio.getPrecos().get(adicional))
                    .reduce(Double::sum).orElse(0.0);

            total += (precoComQuantidade + totalAdicionais) *  qtdShake;
        }

        return total;
    }



    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado){

           if(encontrarPedido(itemPedidoAdicionado)){
               ItemPedido pedidoExistente = itens.stream()
                       .filter(itemPedido -> itemPedido.getShake().equals(itemPedidoAdicionado.getShake()))
                       .findAny()
                       .orElseThrow();

               int quantidade = itemPedidoAdicionado.getQuantidade() + pedidoExistente.getQuantidade();

               pedidoExistente.setQuantidade(quantidade);
           }else{
               itens.add(itemPedidoAdicionado);
           }



    }

    public boolean removeItemPedido(ItemPedido itemPedidoRemovido) {

            if (encontrarPedido(itemPedidoRemovido)) {

                ItemPedido pedidoAtualizado = itens.stream()
                        .filter(itemPedido -> itemPedido.equals(itemPedidoRemovido))
                        .findAny()
                        .orElseThrow();

                pedidoAtualizado.setQuantidade(pedidoAtualizado.getQuantidade() - 1);

                if(pedidoAtualizado.getQuantidade() == 0){
                    this.itens.remove(itemPedidoRemovido);
                    return true;
                }

            } else {
                throw new IllegalArgumentException("Item nao existe no pedido.");
            }

            return true;
    }

    private boolean encontrarPedido(ItemPedido pedido){
        return itens.stream().anyMatch(
                itemPedido -> itemPedido.getShake().equals(pedido.getShake())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id && Objects.equals(itens, pedido.itens) && Objects.equals(cliente, pedido.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itens, cliente);
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
