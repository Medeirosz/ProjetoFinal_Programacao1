package br.feevale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ModalController {

    @FXML
    private ImageView imagemProduto;

    @FXML
    private Label labelNomeProduto;

    @FXML
    private Label labelDescricao;

    @FXML
    private Label labelPreco;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnCancelar;

    private Produto produto;
    private boolean confirmado = false;

    /**
     * Define o produto a ser exibido no modal
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
        carregarDadosProduto();
    }

    /**
     * Carrega os dados do produto nos componentes visuais
     */
    private void carregarDadosProduto() {
        if (produto != null) {
            labelNomeProduto.setText(produto.getNome());
            labelDescricao.setText(produto.getDescricao());
            labelPreco.setText(String.format("R$ %.2f", produto.getPreco()));
            
            // Carrega a imagem do produto (se existir)
            carregarImagem();
        }
    }

    /**
     * Carrega a imagem do produto baseado no tipo
     */
    private void carregarImagem() {
        try {
            String caminhoImagem = obterCaminhoImagem();
            if (caminhoImagem != null) {
                Image imagem = new Image(getClass().getResourceAsStream(caminhoImagem));
                imagemProduto.setImage(imagem);
            }
        } catch (Exception e) {
            System.out.println("Imagem não encontrada para: " + produto.getNome());
        }
    }

    /**
     * Retorna o caminho da imagem baseado no tipo de produto
     */
    private String obterCaminhoImagem() {
        if (produto instanceof CafeExpresso) {
            return "/br/feevale/cafeExpresso.jpg"; 
        } else if (produto instanceof CafePassado) {
            return "/br/feevale/cafePassado.jpg";
        } else if (produto instanceof CafeComLeite) {
            return "/br/feevale/cafeComLeite.jpg";
        } else if (produto instanceof PaoDeQueijo) {
            return "/br/feevale/paoQueijo.png";
        } else if (produto instanceof MistoQuente) {
            return "/br/feevale/mistoQuente.jpg";
        } else if (produto instanceof PaoNaChapa) {
            return "/br/feevale/paoChapa.png";
        }
        return null; 
    }


    /**
     * Ação do botão "Adicionar ao Carrinho"
     */
    @FXML
    private void adicionarAoCarrinho() {
        confirmado = true;
        fecharModal();
    }

    /**
     * Ação do botão "Cancelar"
     */
    @FXML
    private void cancelar() {
        confirmado = false;
        fecharModal();
    }

    /**
     * Fecha o modal
     */
    private void fecharModal() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Verifica se o usuário confirmou a adição
     */
    public boolean isConfirmado() {
        return confirmado;
    }

    /**
     * Retorna o produto
     */
    public Produto getProduto() {
        return produto;
    }
}