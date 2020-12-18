parent(jatin, avantika).
parent(jolly,jatin).
parent(jolly,kattappa).
parent(manisha,avantika).
parent(manisha,shivkami).
parent(bahubali,shivkami).
male(kattappa).
male(jolly).
male(bahubali).
female(shivkami).
female(avantika).
halfsister(X,Y):- female(X),female(Y), parent(A,X),parent(B,X),parent(A,Y),parent(C,Y), not(B==C),not(A==B),not(A==C).
