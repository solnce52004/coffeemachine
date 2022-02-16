package ru.example.coffeemachine.entity;

import javax.persistence.*;

@Entity
@Table(name = "state_machine")
public class StateMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String state;

    @Column(name = "state_machine_context")
    String stateMachineContext;
}
