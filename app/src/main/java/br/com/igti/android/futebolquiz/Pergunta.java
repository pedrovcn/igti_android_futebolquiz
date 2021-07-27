package br.com.igti.android.futebolquiz;

/**
 * Created by maciel on 12/1/14.
 * Alunos: Francis Wander Coelho, Hudson Marques dos Santos, Pedro Vinícius C. do Nascimento
 */
public class Pergunta {
    private int mQuestao;

    private boolean mQuestaoVerdadeira;

    public Pergunta(int questao, boolean questaoVerdadeira) {
        mQuestao = questao;
        mQuestaoVerdadeira = questaoVerdadeira;
    }

    public int getQuestao() {
        return mQuestao;
    }

    public void setQuestao(int questao) {
        mQuestao = questao;
    }

    public boolean isQuestaoVerdadeira() {
        return mQuestaoVerdadeira;
    }

    public void setQuestaoVerdadeira(boolean questaoVerdadeira) {
        mQuestaoVerdadeira = questaoVerdadeira;
    }
}
