package br.org.sudoku.ui.custom.screen;

import br.org.sudoku.service.BoardService;
import br.org.sudoku.ui.custom.button.CheckGameStatusButton;
import br.org.sudoku.ui.custom.button.FinishGameButton;
import br.org.sudoku.ui.custom.button.ResetButton;
import br.org.sudoku.ui.custom.frame.MainFrame;
import br.org.sudoku.ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static javax.swing.JOptionPane.YES_NO_OPTION;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600,600);

    private final BoardService boardService;
    private JButton finishGameButton;
    private JButton checkGameStatusButon;
    private JButton resetButton;

    public MainScreen(final Map<String, String>gameConfig)  {
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addCheckGameStatusButon(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null,"Parabéns você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButon.setEnabled(false);
                finishGameButton.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null,"Seu jogo está errado, ajuste e tente novamente.");
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButon(JPanel mainPanel) {
        checkGameStatusButon = new CheckGameStatusButton(e->{
            var hasErrors= boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch(gameStatus) {
                case NON_STARTED -> "O Jogo não foi inciado";
                case INCOMPLETE -> "O Jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasErrors ? "e contem erros" : "e não contem erros";
            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(checkGameStatusButon);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (dialogResult == 0) {
                boardService.reset();
            }
        });
        mainPanel.add(resetButton);
    }


}
