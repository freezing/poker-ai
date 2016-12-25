package io.freezing.ai.io.parser;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.parse.ParseException;

public interface PokerInputParser {
    PokerState parse(String line) throws ParseException;
}
