# language: pt
# enconding: utf-8
@ui
Funcionalidade: Realizando uma pesquisa pelo Google Search

  @ui_cn_a1
  Esquema do Cenário: Realizar um pesquisa utilizando o Google Search 1
      Dado que eu acesso a "<url>"
    Quando eu preencho o campo de pesquisa com "<texto>"
         E clico no primeiro link da pesquisa
     Então o "<titulo>" deverá ser apresentado

    Exemplos: 
      | url    | texto                  | titulo                                   |
      | Google | selenium documentation | Attention Required! \| Cloudflare        |
      | Google | selenium documentation | selenium documentation - Pesquisa Google |
      | Google | terra notícias         | Erro                                     |

  @ui_cn_a2
  Esquema do Cenário: Realizar um pesquisa utilizando o Google Search 2
      Dado que eu acesso a "<url>"
    Quando eu preencho o campo de pesquisa com "<texto>"
         E clico no primeiro link da pesquisa
     Então o "<titulo>" deverá ser apresentado

    Exemplos: 
      | url    | texto                  | titulo                                   |
      | Google | selenium documentation | Attention Required! \| Cloudflare        |
      | Google | terra notícias         | selenium documentation - Pesquisa Google |
