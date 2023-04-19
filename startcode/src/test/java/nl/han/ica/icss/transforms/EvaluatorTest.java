package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.selectors.TagSelector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EvaluatorTest {
    Evaluator sut = new Evaluator();

    @Test
    void transformTrueIfClause() {
        //arrange
        Stylesheet input = new Stylesheet();
        ASTNode exptectedNode = new Declaration("color").addChild(new ColorLiteral("#124532"));
        input.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(true))
        );
        input.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new IfClause())
                        .addChild(new VariableReference("AdjustColor"))
                        .addChild(exptectedNode)

                )
        );
        Stylesheet output = new Stylesheet();
        output.addChild(new Stylerule().addChild(new TagSelector("p"))
                .addChild(exptectedNode)
        );
        //act
        AST ast = new AST(input);
        sut.apply(ast);
        //assert
        Assertions.assertEquals(output, ast.root);
    }

    @Test
    void transformFalseIfClause() {
        //arrange
        Stylesheet input = new Stylesheet();
        ASTNode exptectedNode = new Declaration("color").addChild(new ColorLiteral("#124532"));
        input.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(false))
        );
        input.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new IfClause())
                        .addChild(new VariableReference("AdjustColor"))
                        .addChild(exptectedNode)

                )
        );
        Stylesheet output = new Stylesheet();
        output.addChild(new Stylerule().addChild(new TagSelector("p")));
        //act
        AST ast = new AST(input);
        sut.apply(ast);
        //assert
        Assertions.assertEquals(output, ast.root);
    }

    @Test
    void removeVariableAssignmentFromBody() {
        Stylesheet input = new Stylesheet();
        ASTNode exptectedNode = new Declaration("color").addChild(new ColorLiteral("#124532"));
        input.addChild((new VariableAssignment())
                .addChild(new VariableReference("AdjustColor"))
                .addChild(new BoolLiteral(false))
        );
        input.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild((new IfClause())
                        .addChild(new VariableReference("AdjustColor"))
                        .addChild(exptectedNode)

                )
        );
    }

    @Test
    void checkAddingVariable() {

    }
}