-- Deleting Tables SQL Script

--Array com os titulos de cada tabela, utlizado em ciclo para que se limpe e elimine cada tabela;
--
--String[] tables = {"Users","Album_Critics","Artists","Albums",
--                "Bands","Genres", "Music_Critics","Musics","Playlists",
--                "Albums_Music","Albums_Artist","Composers","Composers_Album",
--                "Genres_Album","Genres_Music","Labels","Labels_Music","Labels_Album",
--                "Playlists_Musics"};


DELETE FROM tables[i]; -- Elimina os dados na tabela i do array, quando selecionada pelo indice i gerado ciclicamente
DROP TABLE IF EXISTS tables[i] CASCADE; -- Elimina a tabela i selecionada

-- É necessário esclarecer que estes comandos SQL são utilizados em ciclo na classe Init(), função terminate_database(), linha 154

--Deleting data from tables

--delete_playlist()
DELETE FROM Playlists_Musics WHERE playlist_id = play_id; -- Elimina os dados referentes à tabela Playlists_Musics cuja query verifque que playlist_id é igual ao id fornecido por escolha do Utilizador,
                                                          -- com o objetivo de eliminar as musicas da tabelas que façam referência à playlist assinada

--delete_playlist()
DELETE FROM Playlists WHERE playlist_id = play_id; -- Elimina os dados referentes à tabela Playlists cuja query verifque que playlist_id é igual ao id fornecido por escolha do Utilizador,
                                                  --  desta vez assumindo que já eliminou da tabela Playlists_Musics irá eliminar permanentemente quaisquer registos que exitissem desta playlist.

--delete_account()
DELETE FROM Album_Critics WHERE email = 'user.getUsername()'; -- Na sequencia de querer eliminar a conta dos registos esta query permitirá a eliminação permanente de quaisquer criticas a Albums, na tabela Album_Critics
                                                              -- que façam referência ao utilizador em questão

--delete_account()
DELETE FROM Music_Critics WHERE email = 'user.getUsername()'; -- Na sequencia de querer eliminar a conta dos registos esta query permitirá a eliminação permanente de quaisquer criticas a Musicas, na tabela Music_Critics,
                                                              -- que façam referência ao utilizador em questão

--delete_account()
DELETE FROM Playlists WHERE email = 'user.getUsername()'; -- Na sequencia de querer eliminar a conta dos registos esta query permitirá a eliminação permanente de quaisquer Playlists
                                                          -- que façam referência ao utilizador em questão

--delete_account()
DELETE FROM Users WHERE email = 'user.getUsername()'; -- Finalmente a eliminação dos registos do utlizador na tabela do Users. Neste ponto, toda a sua informação secundária já foi eliminada.
