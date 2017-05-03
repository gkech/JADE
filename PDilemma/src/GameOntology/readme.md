
# Ontology for JADE Prisoner's Dilemma Example (info updated code only)

## Our Basic Information Objects
These are the classes(Beans) containing all the information-objects we want agents to exchange
```
Game.java
GameAction.java
PersonalPayoff.java
```
## GameOntology
`GameOntology.java` contains our ontology schema using BeanOntology

## Behaviours and Ontologies
We implemented message sending and receiving code (from strings to objects and vice versa) inside:
1. `HostBehaviour.java` which contains the behaviour implementation for HostAgent
2. 'PlayBehaviour'  whcih contains the behaviour implementation for Prisoner Dilemma Players

For each of these classes, we had to declare codec and ontology, in order to "speak" the same language and "know" the same ontology for message parsing purposes.
