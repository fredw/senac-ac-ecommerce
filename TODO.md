# Requisitos
- Resolver @TODO dos fontes
- Carrinho de compras
    - Implementar restrição de login para efetivação da compra
    - Alterar para pegar o cliente logado (e não fixo o id 1)
- Usuário
    - Cadastro do usuário: corrigir problema nos erros/validação do cadastro;
    - Implementar login/logout;
- Painel
    - Implementar login;
    - Implementar manutenção de produtos;
        - Implementar upload de imagem e PDF (está dando problema ao no auto-bind, definido o multipart em um campo que é string)
        - Na exclusão excluir os arquivos caso existam
    - Implementar configurações de segurança para permitir somente usuários logados e ADMIN;
    
# Extras/Adicionais
- Cadastro do usuário: implementar validação de e-mail repetidos
- Cadastro do usuário: implementar confirmação de e-mail
- Cadastro de produto: implementar validação de código repetido