package Grammar.AST;

import Game_state.Game.Game;
import Game_state.Game.Game_im;
import Game_state.Game.ReadData;
import Grammar.AST.State.AssignmentNode;
import Grammar.AST.State.DoneNode;
import Grammar.AST.State.IfNode;
import Grammar.AST.Expr.NumberNode;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Game game = new Game_im("ball", "boss", ReadData.createMap());
    Node.StateNode node, trueNode = new DoneNode(), falseNode = new DoneNode();
    Node.ExprNode expr;
    Map<String, Long> map = game.getIdentifiers();
    String var;
    @Test
    void testIfNode(){
        expr = new NumberNode(5);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));

        expr = new NumberNode(1);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));

        expr = new NumberNode(0);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(falseNode, node.evaluate(game));

        expr = new NumberNode(-1);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));

        expr = new NumberNode(-5);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));
    }
    @Test
    void testWhile(){
        expr = new NumberNode(5);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));

        expr = new NumberNode(1);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));

        expr = new NumberNode(0);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(falseNode, node.evaluate(game));

        expr = new NumberNode(-1);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));

        expr = new NumberNode(-5);
        node = new IfNode(expr, trueNode, falseNode);
        assertEquals(trueNode, node.evaluate(game));
    }
    @Test
    void testAssignment(){
        var = "a";
        node = new AssignmentNode(var, new NumberNode(5));
        node.evaluate(game);
        assertEquals(5, map.get(var));

        var = "b";
        node = new AssignmentNode(var, new NumberNode(1));
        node.evaluate(game);
        assertEquals(1, map.get(var));

        var = "c";
        node = new AssignmentNode(var, new NumberNode(0));
        node.evaluate(game);
        assertEquals(0, map.get(var));

        var = "d";
        node = new AssignmentNode(var, new NumberNode(-1));
        node.evaluate(game);
        assertEquals(-1, map.get(var));

        var = "e";
        node = new AssignmentNode(var, new NumberNode(-5));
        node.evaluate(game);
        assertEquals(-5, map.get(var));
    }
    @Test
    void testEval(){
        var = "v";
        node = new AssignmentNode(var, new NumberNode(5));
        node.evaluate(game);
        assertEquals(5, map.get(var));

        var = "w";
        node = new AssignmentNode(var, new NumberNode(1));
        node.evaluate(game);
        assertEquals(1, map.get(var));

        var = "x";
        node = new AssignmentNode(var, new NumberNode(0));
        node.evaluate(game);
        assertEquals(0, map.get(var));

        var = "y";
        node = new AssignmentNode(var, new NumberNode(-1));
        node.evaluate(game);
        assertEquals(-1, map.get(var));

        var = "z";
        node = new AssignmentNode(var, new NumberNode(-5));
        node.evaluate(game);
        assertEquals(-5, map.get(var));
    }
}