package br.feevale;

public class CafePassado extends Cafe {
    public CafePassado() {
        super(
            "Café Passado",
            4.50,
            "Café coado tradicional",
            "Passado",
            "Quente"
        );
    }
    
    @Override
    public String getDescricao() {
        return "Café coado tradicional";
    }
}