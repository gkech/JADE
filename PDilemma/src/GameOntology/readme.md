
# Ontology for JADE Prisoner's Dilemma Example 
`(updated code only based on /src/Sequential repo)`

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
2. `PlayBehaviour.java`  which contains the behaviour implementation for Prisoner Dilemma Players

For each of these classes, we had to declare codec and ontology, in order to "speak" the same language and "know" the same ontology for message parsing purposes.
```java
    /**
     * Example Code
     * SubBehaviour for game(Prisoner's dilemma) receiving and action sending  
     */
    public class ReceiveGameSelectAction_Behaviour extends Behaviour{
    	private static final long serialVersionUID = 1L;
    	private boolean finish=false;
    	private String action=null;
    	
        public ReceiveGameSelectAction_Behaviour(Agent a) {
            super(a);
        }
   
		public void action(){
			ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (msg != null){
				try {
					//transform msg string into Game Object with extractContent
					ContentElement ce = manager.extractContent(msg);
					Game g = (Game) ce;
					if (g.getGameName().equals("Prisoner's Dilemma")){
						//select action...
						action=SelectAction();
						System.out.println("Agent : "+myAgent.getName()+" plays "+action);
						
						GameAction ga = new GameAction();
						ga.setAgentAction(action);
						ACLMessage reply = msg.createReply();
						manager.fillContent(reply, ga);
						//...send ontology based reply 
						myAgent.send(reply);
						finish=true; //when done with this message, go done()
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				//if no message has arrived, block this behaviour (and save resources)
				System.out.println( myAgent.getAID().getName()+" doesnt have any request message!" );
				block();
			}
		}
		public boolean done() {	//here done
			return finish;
		}
    }
```
