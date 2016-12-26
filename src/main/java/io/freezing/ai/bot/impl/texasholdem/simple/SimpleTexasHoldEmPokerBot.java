package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.bot.action.impl.CheckAction;
import io.freezing.ai.bot.action.impl.FoldAction;
import io.freezing.ai.bot.action.impl.RaiseAction;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.domain.Table;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SimpleTexasHoldEmPokerBot implements PokerBot {
    private final TexasHoldEmRules rules;

    public SimpleTexasHoldEmPokerBot(TexasHoldEmRules rules) {
        this.rules = rules;
    }

    @Override
    public BotAction nextAction(PokerState state) {
        // TODO: Implement, first just calculate the probability
        Table table = state.getTable();
        Hand myHand = state.getMyHand();

        double winProbability = calculateWinProbability(table, myHand);
        BotActionRationale rationale = new SimpleTexasHoldEmRationale(winProbability);

        if (winProbability > 90) return new RaiseAction(state.getMyStack(), rationale);
        else if (winProbability > 50) return new RaiseAction(state.getMyStack() / 2, rationale);
        else if (state.getAmountToCall() == 0) return new CheckAction(rationale);
        else return new FoldAction(rationale);
    }

    private double calculateWinProbability(Table table, Hand hand) {
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        return SimpleTexasHoldEmPokerBot.class.getSimpleName();
    }
}
