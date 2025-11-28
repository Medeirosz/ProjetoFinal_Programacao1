package br.feevale;

public class CafeExpresso extends Cafe {
    public CafeExpresso() {
        super("Café Expresso",
        6.00, 
        "Café expresso puro e encorpado",
        "Expresso",
        "Quente"
    );
    }
    
    @Override
    public String getDescricao() {
        return "Café expresso puro e encorpado";
    }
}