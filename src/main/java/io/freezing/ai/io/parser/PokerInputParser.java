package io.freezing.ai.io.parser;

import io.freezing.ai.domain.PokerState;

public interface PokerInputParser {
    PokerState parse(String line);
}
