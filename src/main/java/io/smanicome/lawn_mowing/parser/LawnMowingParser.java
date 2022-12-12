package io.smanicome.lawn_mowing.parser;

import io.smanicome.lawn_mowing.core.LawnToMow;
import io.smanicome.lawn_mowing.exceptions.InvalidInputException;

public interface LawnMowingParser {
    LawnToMow parse(String filepath) throws InvalidInputException;

}
