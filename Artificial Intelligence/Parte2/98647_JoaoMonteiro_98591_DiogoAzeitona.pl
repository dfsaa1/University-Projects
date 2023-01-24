%next_player(Player1, Player2) - permite saber qual é o próximo jogador
next_player(1,2).
next_player(2,1).

%play_game/0
% Este predicado começa por criar um tabuleiro com a dimensao 6x6 e
% validar a jogada do computador (player 1).
play_game():-
    initial_board(6, 6, B0),
    play(1,B0).

%exercicio A
%-----------------------------------------------------------
initial_board(0,0,[]).
initial_board(NumRows,NumColumns,Board):-
    zero_matrix(NumRows,NumColumns,Board).

zero_vetor(0, []) :- !.
zero_vetor(M, [0|Ks]) :-
    M1 is M - 1,
    zero_vetor(M1, Ks).

zero_matrix(0, _, []) :- !.
zero_matrix(N, M, [K|Ks]) :-
    N1 is N - 1,
    zero_vetor(M, K),
    zero_matrix(N1, M, Ks).

%play/1
%O predicado play tem como argumentos o jogador que deve jogar e o tabuleiro sobre o qual deve fazer a sua jogada.
% Este predicado e' recursivo de modo a permitir a alternancia das
% jogadas. A sua implementaçao e constituida pela jogada do computador
% (play(1,B)) , pela jogada do jogador (play(2,B)) e pela verificaçao
% do fim do jogo.
play(_,B0):-
    game_over(B0,T),
    calculate_board_value(T,Value),
    print_board(B0),
    write('Game Over!'),
    nl,
    display(Value),
    !.

play(1,B0):-
    write('Player:'),nl,
    print_board(B0),
    alphabeta(B0,6,-100,100,B1,_,1),
    !,
    play(2,B1).

play(2,B0):-
    write('Computer:'),nl,
    print_board(B0),
    write('Where to play? (C,L)'),
    read(C),
    read(L),
    valid_move(C,L,B0),
    add_move(2,C,L,B0,B1),
    !,
    play(1,B1).

%exercicio B
%-----------------------------------------------------------------
print_board([]).
print_board([H|T]):-
	print_lista(H),
        nl,
        print_board(T),
	write('----').

print_lista([]).
print_lista([H|T]):-
    write(H),
    print_lista(T).

%exercicio C
%------------------------------------------------------------------
valid_move(0,B,[Head|_]):-
   move(B,Head),
   !.

valid_move(A,B,[_|Board]):-
   A1 is A - 1,
   valid_move(A1,B,Board).

move(0,[Head|_]):-
    Head = 0,
    !.

move(B,[_|Tail]):-
    B1 is B - 1,
    move(B1,Tail).

%exercicio D
%-------------------------------------------------------------------
add_move(Player,X,Y,InitialBoard,FinalBoard):-
  valid_move(X,Y,InitialBoard),
  get_lista((X,InitialBoard),Lista),
  replace(Y,Lista,Player,Aux),
  replace(X,InitialBoard,Aux,FinalBoard).

get_lista((X,List),Result):-
    nth0(X,List,Result).

%exercicio E
%-------------------------------------------------------------------
generate_move(P, InitialBoard, FinalBoard) :-
        length(InitialBoard, L),
        generate_move_aux(P, InitialBoard, FinalBoard, 0, L).

	findPos([H|_],0, H).
        findPos([_|T], Value, L):-
            Value1 is Value-1,
            findPos(T, Value1, L).

        generate_move_aux(_,_ ,_, L, L) :- !.
        generate_move_aux(P, InitialBoard, FinalBoard, Nr,_ ) :-
            findPos(InitialBoard, Nr, List),
            nth0(N, List, 0),
            replace(N, List, P, Result),
            replace(Nr, InitialBoard, Result, FinalBoard).


        generate_move_aux(P, InitialBoard, FinalBoard, Nr, Length) :-
            Nr2 is Nr+1,
            generate_move_aux(P, InitialBoard, FinalBoard, Nr2, Length).

        replace(I, Lista, Value, Result) :-
            nth0(I, Lista, _, Aux),
            nth0(I, Result, Value, Aux).

%exercicio F
%-------------------------------------------------------------------
game_over(Board,X):-
    append(_,[C|_], Board),
    append(_,[X,X,X,X|_],C),
    !.
game_over(Board,X):-
    append(_,[C1,C2,C3,C4|_],Board),
    append(I1,[X|_],C1),
    append(I2,[X|_],C2),
    append(I3,[X|_],C3),
    append(I4,[X|_],C4),
    length(I1,M1), length(I2,M2), length(I3,M3), length(I4,M4),
    M2 is M1+1, M3 is M2+1, M4 is M3+1,
    !.
game_over(Board,X):-
    append(_,[C1,C2,C3,C4|_],Board),
    append(I1,[X|_],C1),
    append(I2,[X|_],C2),
    append(I3,[X|_],C3),
    append(I4,[X|_],C4),
    length(I1,M), length(I2,M), length(I3,M), length(I4,M),
    !.
game_over(Board,X):-
    append(_,[C1,C2,C3,C4|_],Board),
    append(I1,[X|_],C1),
    append(I2,[X|_],C2),
    append(I3,[X|_],C3),
    append(I4,[X|_],C4),
    length(I1,M1), length(I2,M2), length(I3,M3), length(I4,M4),
    M2 is M1-1, M3 is M2-1, M4 is M3-1,
    !.

%exercicio G
%-------------------------------------------------------------------
calculate_board_value(Winner, Score):-
    Winner is 1,
    Score is 1,
    !.
calculate_board_value(Winner, Score):-
    Winner is 2,
    Score is -1,
    !.
calculate_board_value(Winner, Score):-
    Winner is 0,
    Score is 0,
    !.

%alphabeta/7
%minimax-alpha-beta
% O predicado que implementa o minimax e' chamado alfabeta e tem como
% argumentos o tabuleiro, o valor de profundidade que ainda e' permitido
% explorar, o alfa, o beta, o tabuleiro resultado, o score da avaliaçao
% do resultado na o'tica do computador e o jogador que se esta' a
% avaliar (minimizar ou a maximizar).
alphabeta(Bi, 0, _, _, Bi, Value, P):-
    calculate_board_heuristic(P,Bi,Value),
    !.

alphabeta(Bi, _, _, _, Bi, Value, _):-
    game_over(Bi,T),
    calculate_board_value(T,Value),
    !.


alphabeta(Bi, D, Alfa, Beta, Bf, Value, Player):-
    next_player(Player,Other),
    possible_moves(Player,Bi,L),
    !,
    evaluate_child(Other, L, D, Alfa, Beta, Bf, Value).

%evaluate_child/7
evaluate_child(Player, [B], D, Alfa, Beta, B, Value):-
    D1 is D-1,
    !,
    alphabeta(B, D1, Alfa, Beta, _, Value, Player).


evaluate_child(2, [Bi|T], D, Alfa, Beta,Bf, Value):-
    D1 is D-1,
    alphabeta(Bi, D1, Alfa, Beta, _, Value1, 2),
    !,
    evaluate_next_child_max(Value1,Bi, T, D, Alfa, Beta, Value, Bf).

evaluate_child(1, [Bi|T], D, Alfa, Beta,Bf, Value):-
    D1 is D-1,
    alphabeta(Bi, D1, Alfa, Beta, _, Value1, 1),
    !,
    evaluate_next_child_min(Value1,Bi, T, D, Alfa, Beta, Value, Bf).

%evaluate_next_child_max/8
evaluate_next_child_max(Value1,Bi, T, D, Alfa, Beta, Value, Bf):-
    Value1 < Beta,
    max(Value1,Alfa,NewAlfa),
    !,
    evaluate_child(2, T, D, NewAlfa, Beta, B2, Value2),
    max_board(Value1,Bi,Value2,B2,Value,Bf).

evaluate_next_child_max(Value1,Bi, _, _, _, _, Value1, Bi):- !.

%evaluate_next_child_min/8
evaluate_next_child_min(Value1,Bi, T, D, Alfa, Beta, Value, Bf):-
     Value1 > Alfa,
     min(Value1,Beta,NewBeta),
     !,
     evaluate_child(1, T, D, Alfa, NewBeta, B2, Value2),
     min_board(Value1,Bi,Value2,B2,Value,Bf).

evaluate_next_child_min(Value1,Bi, _, _, _, _, Value1, Bi):- !.


%possible_moves/3
possible_moves(X,B,L):-
    bagof(BP,generate_move(X,B,BP),L).

%max_board/6
max_board(Value1,B1,Value2,_,Value1,B1):-
    Value1 >= Value2.

max_board(Value1,_,Value2,B2,Value2,B2):-
    Value1 < Value2.

%min_board/6
min_board(Value1,B1,Value2,_,Value1,B1):-
    Value1 =< Value2.

min_board(Value1,_,Value2,B2,Value2,B2):-
    Value1 > Value2.

%max/3
max(X,Y,X):-
    X>=Y,!.
max(X,Y,Y):-
    Y>X.
%min/3
min(X,Y,X):-
    X=<Y,!.
min(X,Y,Y):-
    Y<X.
