package com.mowitnow.steps;

import com.mowitnow.mower.domain.area.Coordinates;
import com.mowitnow.mower.domain.area.Orientation;
import com.mowitnow.mower.domain.area.Position;
import com.mowitnow.mower.domain.command.Command;
import com.mowitnow.mower.domain.command.Movement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CammandStep {

    private Command command;
    private Position position;

    @Given("the initial position {int} {int} {string} and {int} {int}")
    public void the_initial_position(Integer x, Integer y, String direction, Integer PelouseX, Integer PelouseY) {
        Position p = new Position(new Coordinates(x, y), Orientation.findOrientationFromCode(direction));
        command = Command.builder().position(p).topRightCoordinates(new Coordinates(PelouseX, PelouseY)).build();
    }

    @Given("with the following {string}")
    public void with_the_following_movement(String instructionsAsString) {
        List<Movement> instructions = new ArrayList<>();
        char[] instructionsAsCharacters = instructionsAsString.toCharArray();
        for (char c : instructionsAsCharacters) {
            instructions.add(Movement.findMovementFromCode(c));
        }
        command.setMovements(instructions);
    }

    @When("I move the mower with the following movements")
    public void i_move_the_tondeuse_with_the_following_instructions() {
        position = command.processCommandMovement();
    }

    @Then("the final position is {int} {int} {string}")
    public void the_final_position_is(Integer x, Integer y, String direction) {
        assertThat(position).isNotNull();
        assertThat(position.getCoordinates().getX()).isEqualTo(x);
        assertThat(position.getCoordinates().getY()).isEqualTo(y);
        assertThat(position.getOrientation().getCode()).isEqualTo(direction);
    }

}