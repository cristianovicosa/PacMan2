Base para o jogo Pacman.

Pontos dessa etapa: 5 pontos.

Configuração inicial:
- (Se ainda não tiver feito) Baixe o projeto EngineJogo, seja via git clone ou baixando o código. Adicione-o ao seu NetBeans.
Caso dê problema de JDK, clique no projeto com o botão direito do mouse e clique em "Propriedades". Clique em "Bibliotecas",
mude a plataforma Java para a plataforma default do seu computador.
- Faça um Fork deste projeto para o seu GitHub (como o trabalho é em dupla, é necessário apenas uma pessoa da dupla fazer 
isso). 
- Faça um git clone do seu projeto na sua máquina. Assim você será capaz de fazer commits, push e pull. Pode ocorrer o mesmo
problema de biblioteca, caso ocorra, resolva da mesma forma.

Detalhes importantes:
- Dentro do projeto há uma pasta chamada "resources", dentro desta estão duas pastas, "mapas" e "imagens". Dentro destas
estão as imagens básicas e os mapas básicos.
- Um bom site de imagens para jogos simples é esse: http://opengameart.org

A fazeres:
- Exibir o bloco que foi carregado. Nos mapas que estão incluídos neste projeto a posição para o bloco é indicada com 1.
Deve-se carregar a imagem do bloco em todas as posições iguais a 1.
- Exibir a moeda que foi carregada. Nos mapas que estão incluídos neste projeto a posição para a moeda é indicada com 2.
Deve-se carregar a imagem da moeda em todas as posições iguais a 2.
- Fazer o pacman andar dentro dos limites da tela e não passando dentro dos blocos. Isso foi mostrado em sala de aula como
pode ser feito. Há muitas maneiras de se fazer, você tem liberdade de fazer de uma maneira diferente da que foi mostrada
em sala, porém é necessário que funcione!
- Fazer o jogo armazenar a posição que se deseja virar, virando só quando pode (assim como no trabalho anterior).
- Fazer o pacman comer as moedas. No código já existe uma matriz para armazenar onde o pacman já passou. Sempre que ele já
passou numa posição, não desenhe a imagem da moeda.
- Carregar imagens diferentes das que foram disponibilizadas, para o bloco e as pílulas (moedas).

Ponto extra (2 pontos):
- Terminar o jogo quando comer todas as moedas.
- Carregar uma imagem para as moedas que faça-as ficar "rodando", dando a aparência de que estão se mexendo.
- Carregar um pacman diferente.
