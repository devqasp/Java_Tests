# language: pt
# enconding: utf-8
@ui
Funcionalidade: Realizando uma pesquisa pelo Google Search

  @ui_cn_b1
  Esquema do Cenário: Realizar um pesquisa utilizando o Google Search
      Dado que eu acesso a "<url>"
    Quando eu preencho o campo de pesquisa com "<texto>"
         E clico no primeiro link da pesquisa
     Então o "<titulo>" deverá ser apresentado

    Exemplos: 
      | url    | texto                  | titulo                            |
      | Google | selenium documentation | Attention Required! \| Cloudflare |
      | Google | terra notícias         | Erro                              |
