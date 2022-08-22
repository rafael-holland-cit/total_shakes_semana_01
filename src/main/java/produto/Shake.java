package produto;

import ingredientes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Shake {
    private Base base;
    private Fruta fruta;
    private Topping topping;
    private List<Adicional> adicionais = new ArrayList<>();
    private TipoTamanho  tipoTamanho;

    public Shake(Base base, Fruta fruta, Topping topping, TipoTamanho tipoTamanho) {
        this.base = base;
        this.fruta = fruta;
        this.topping = topping;
        this.tipoTamanho = tipoTamanho;
    }

    public Shake(Base base, Fruta fruta, Topping topping, List<Adicional> adicionais, TipoTamanho tipoTamanho) {
        this.base = base;
        this.fruta = fruta;
        this.topping = topping;
        this.adicionais = ordenarLista(adicionais);
        this.tipoTamanho = tipoTamanho;

    }

    public List<Adicional> ordenarLista(List<Adicional> adicionais){
        return adicionais.stream().sorted().collect(Collectors.toList());
    }

    public Base getBase() {
        return base;
    }

    public Fruta getFruta() {
        return fruta;
    }

    public Topping getTopping() {
        return topping;
    }

    public List<Adicional> getAdicionais() {
        List<Adicional> listaOrdenada = adicionais.stream().sorted().collect(Collectors.toList());
        return listaOrdenada;
    }

    public TipoTamanho getTipoTamanho() {
        return tipoTamanho;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shake)) return false;
        Shake shake = (Shake) o;
        return Objects.equals(base, shake.base) && Objects.equals(fruta, shake.fruta) && Objects.equals(topping, shake.topping) && Objects.equals(adicionais, shake.adicionais) && tipoTamanho == shake.tipoTamanho;
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, fruta, topping, adicionais, tipoTamanho);
    }

    @Override
    public String toString() {
        return this.base.getTipoBase().toString() + " / " + this.fruta.getTipoFruta().toString() + " / " + this.topping.getTipoTopping().toString() + " / " + this.adicionais + " / " + this.tipoTamanho.toString();
    }
}
