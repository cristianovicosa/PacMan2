package pacmanbase;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author viniciusduarte
 */
public class PacmanBase_fase1 extends base.Jogo {

    //Objeto para armazenar o PNG do pacman.
    BufferedImage pacman, bloco, pilula, pilula1, pilula2;
    
    //Tamanho fixo de cada boneco, em pixels.
    final int TAM = 28;    
    //Mapa.
    private ArrayList<String> mapa;

    //Lugares onde o pacman já passou.
    private boolean[][] passei;

    private int totalLinhas;
    private int totalColunas;

    /*
     pac_pix_x -> posicao do pacman no eixo x em pixels.
     pac_pix_y -> posicao do pacman no eixo y em pixels.
     pac_map_x -> posicao do pacman no eixo x com base no mapa de caracteres.
     pac_map_y -> posicao do pacman no eixo y com base no mapa de caracteres.
     */
    private int pac_pix_x, pac_pix_y, pac_map_x, pac_map_y;
    private int direcao, quadro, contador_de_atualizacoes ,direcaoFutura,temPilula=1,viramoeda;
    List sorte = new ArrayList();
    /*Contagem inicial das pilulas*/
    
    public PacmanBase_fase1() {
        this.titulo = "Pacman Cristiano e Samuel Henrrique";
        contador_de_atualizacoes = 0;

        //Carregando todas as imagens básicas.
        try {
            pacman = ImageIO.read(new File("resources/imagens/packman2.png"));
        } catch (IOException ex) {
            System.err.println("Imagem pacman falhou: " + ex);
        }

        try {
            bloco = ImageIO.read(new File("resources/imagens/block2.jpg"));
        } catch (IOException ex) {
            System.err.println("Imagem bloco falhou: " + ex);
        }

        try {
            pilula1 = ImageIO.read(new File("resources/imagens/moG.png"));
            pilula2 = ImageIO.read(new File("resources/imagens/moP.png"));
            pilula = pilula2;
        } catch (IOException ex) {
            System.err.println("Imagem pilula falhou: " + ex);
        }
    
        //Leitura do mapa.
        Scanner s;
        try {
            s = new Scanner(new File("resources/mapas/simples_1.txt"));
            mapa = new ArrayList<>();
            while (s.hasNextLine()) {
                mapa.add(s.next());
            }
            s.close();

            totalLinhas = mapa.size();
            totalColunas = mapa.get(0).length();

            System.out.println("Resolução da tela:");
            System.out.println(TAM * totalLinhas + " x " + TAM * totalColunas);
            

            //Pegar a posição inicial do pacman. Onde está o 0 no mapa.
            int linhaInicial = 0;
            while (!mapa.get(linhaInicial).contains("0")) {
                linhaInicial++;
            }
            
            pac_pix_y = linhaInicial * TAM;
            pac_pix_x = mapa.get(linhaInicial).indexOf('0') * TAM;
            pac_map_x = mapa.get(linhaInicial).indexOf('0');
            pac_map_y = linhaInicial;
            //Inicializando a matriz que armazena posicoes que o pacman ja passou
            passei = new boolean[totalColunas][totalLinhas];
            //Nao passou em lugar nenhum, no inicio
            for (int i = 0; i < totalColunas; i++) {
                for (int j = 0; j < totalLinhas; j++) {
                    passei[i][j] = false;
                    if(charAt(i,j)=='2'){
                        temPilula++;
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.err.println("Mapa falhou: " + ex);
        }

        //Começa indo para a direita.
        direcao = KeyEvent.VK_RIGHT;
        direcaoFutura = KeyEvent.VK_RIGHT;
        quadro = 0;
    }

    @Override
    public void inicia() {
        this.atraso = 40;
    }

    
    @Override
    
    public void desenha(Graphics2D g) {
        //Tem que desenhar os blocos e as pilulas aqui.
        //Se o pacman já passou na posicao da pilula, nao desenha.

        g.drawImage(pacman.getSubimage(quadro * 30, (direcao - 37) * 30, TAM, TAM),
            pac_pix_x, pac_pix_y, null);

        //Para desenhar uma imagem adaptando-a ao tamanho TAMxTAM (redimencionando):
        /*
         g.drawImage(imagem, posicao_inicial_do_desenho_x, 
                    posicao_inicial_do_desenho_y, 
                    posicao_inicial_do_desenho_x + TAM, 
                    posicao_inicial_do_desenho_y + TAM,
                    posicao_inicial_do_recorte_x, 
                    posicao_inicial_do_recorte_y, 
                    posicao_final_do_recorte_x, 
                    posicao_final_do_recorte_y, null);
        
        EXEMPLO:
        g.drawImage(bloco, x, y, x + TAM, y + TAM,
                0, 0, bloco.getWidth(), bloco.getHeight(), null);
         */
        for (int i = 0; i < totalLinhas; i++) {
            for (int j = 0; j < totalColunas; j++) {
                if(charAt(i, j)=='1'){
                    g.drawImage(bloco,j*TAM,i*TAM,TAM,TAM,null);
                }
                else{
                    if (passei[i][j] == false && (charAt(i, j)== '2')){
                        g.drawImage(pilula,j*TAM,i*TAM,TAM,TAM,null);
                    }
                }                
            }
        }
        g.drawImage(pacman.getSubimage(quadro * 30, (direcao - 37) * 30, TAM, TAM),pac_pix_x, pac_pix_y, null);
    }

    @Override
    public void atualiza() {
        if(viramoeda==5){
            if(pilula == pilula1){
                pilula = pilula2;
            }
            else{
                pilula = pilula1;
            }
            viramoeda=0;
        }
        viramoeda++;
        if (temPilula==0){
            JOptionPane.showMessageDialog(null,"PARABÉNS! VOCÊ GANHOU!");
            System.exit(0);
        }
        if (direcaoFutura == KeyEvent.VK_RIGHT && pac_map_x < totalColunas-1 && charAt(pac_map_y,pac_map_x+1)!='1' && pac_pix_y % TAM == 0){
            direcao = direcaoFutura;
        }
        if (direcaoFutura == KeyEvent.VK_LEFT && pac_map_x > 0 && charAt(pac_map_y,pac_map_x-1)!='1' && pac_pix_y % TAM == 0){
            direcao = direcaoFutura;
        }
        if (direcaoFutura == KeyEvent.VK_DOWN && pac_map_y < totalColunas - 1 && charAt(pac_map_y+1,pac_map_x)!='1' && pac_pix_x % TAM == 0){
            direcao = direcaoFutura;
        }
        if (direcaoFutura == KeyEvent.VK_UP && charAt(pac_map_y-1,pac_map_x)!='1' && pac_pix_x % TAM == 0){
            direcao = direcaoFutura;
        }
        quadro++;
        contador_de_atualizacoes++;

        if (quadro == 3) {
            quadro = 0;
        }

        switch (direcao) {
            case KeyEvent.VK_RIGHT:
                 if (pac_map_x < totalColunas - 1 && (charAt(pac_map_y,pac_map_x+1)!='1') && pac_pix_y % TAM == 0) {
                    if(pac_pix_x + TAM == totalColunas*TAM-2){
                        if(passei[pac_map_y][pac_map_x+1] == false){
                           passei[pac_map_y][pac_map_x+1] = true;
                           temPilula--;
                        }
                        pac_map_x = 0;
                        pac_pix_x = 0;
                    }
                    else{       
                        if(passei[pac_map_y][pac_map_x] == false){
                            passei[pac_map_y][pac_map_x] = true;
                            temPilula--;
                        }
                            pac_pix_x = pac_pix_x+2;
                            pac_map_x = pac_pix_x/TAM;
                    }
                 }
                break;
            case KeyEvent.VK_LEFT:
                 if (pac_map_x > 0 && (charAt(pac_map_y,pac_map_x-1)!='1') && pac_pix_y % TAM == 0) {
                    if(pac_pix_x-2==0){
                        if(passei[pac_map_y][0] == false){
                            passei[pac_map_y][0] = true;
                            temPilula--;
                        }       
                        pac_map_x = totalColunas-1;
                        pac_pix_x=totalColunas*TAM - TAM;
                    }
                    else{
                         if(passei[pac_map_y][pac_map_x] == false){
                            passei[pac_map_y][pac_map_x] = true;
                            temPilula--;
                        }                        
                        pac_pix_x=pac_pix_x-2;
                        pac_map_x = (pac_pix_x+TAM-1)/TAM;
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (pac_map_y < totalLinhas - 1 && (charAt(pac_map_y+1,pac_map_x)!='1') && pac_pix_x % TAM == 0) {
                    if(pac_pix_y + TAM == totalLinhas*TAM-2){
                        if(passei[totalColunas-1][pac_map_x] == false){
                            passei[totalColunas-1][pac_map_x] = true;
                            temPilula--;
                        } 
                        pac_pix_y=0;
                        pac_map_y=0;
                    }
                    else{
                        if(passei[pac_map_y][pac_map_x] == false){
                            passei[pac_map_y][pac_map_x] = true;
                            temPilula--;
                        } 
                        pac_pix_y=pac_pix_y+2;
                        pac_map_y = pac_pix_y/TAM;
                    }
                }
                break;
            case KeyEvent.VK_UP:
                 if (pac_map_y > 0 && (charAt(pac_map_y-1,pac_map_x)!='1') && pac_pix_x % TAM == 0){
                    if(pac_pix_y-2==0){
                        if(passei[0][pac_map_x] == false){
                            passei[0][pac_map_x] = true;
                            temPilula--;
                        } 
                       pac_pix_y = totalLinhas * TAM-TAM;
                       pac_map_y = totalLinhas-1;
                    }
                    else{
                        if(passei[pac_map_y][pac_map_x] == false){
                            passei[pac_map_y][pac_map_x] = true;
                            temPilula--;
                        } 
                        pac_pix_y=pac_pix_y-2;
                        pac_map_y = (pac_pix_y+TAM-1)/TAM;
                    }    
                }
                break;
        }
    }

    //Retorna o caracter que está na posição linha x coluna
    private char charAt(int linha, int coluna) {
        return mapa.get(linha).charAt(coluna);
    }

    //Método para responder ao teclado, somente setas.
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key > 36 && key < 41) {
            if (key == KeyEvent.VK_RIGHT && charAt(pac_map_y,pac_map_x+1) != '1'&& pac_pix_y % TAM == 0){
                direcao = key;
            }
            else{
                direcaoFutura=key;
            }
            if (key == KeyEvent.VK_LEFT && charAt(pac_map_y,pac_map_x-1) != '1'&& pac_pix_y % TAM == 0){
                direcao = key;
            }
            else{
                direcaoFutura=key;
            }
            if (key == KeyEvent.VK_UP && charAt(pac_map_y-1,pac_map_x) != '1'&& pac_pix_x % TAM == 0){
                direcao = key;
            }
            else{
                direcaoFutura=key;
            }
            if (key == KeyEvent.VK_DOWN && charAt(pac_map_y+1,pac_map_x) != '1'&& pac_pix_x % TAM == 0){
                direcao = key;
            }
            else{
                direcaoFutura=key;
            }
        }
    }
}
