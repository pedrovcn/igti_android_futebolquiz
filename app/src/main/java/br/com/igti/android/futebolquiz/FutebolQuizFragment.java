package br.com.igti.android.futebolquiz;

import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FutebolQuizFragment extends Fragment {

    private static final String TAG = "FutebolQuizFragment";

    private static final String KEY_INDICE = "indice";

    private Button mBotaoVerdade;
    private Button mBotaoFalso;
    private TextView mConteudoCard;
    private CardView mCardView;

    private int mIndiceAtual = 0;

    private static final String PREF_PRIMEIRA_VEZ = "primeiraVez";

    private Pergunta[] mPerguntas = new Pergunta[]{
            new Pergunta(R.string.cardview_conteudo_joinville,true),
            new Pergunta(R.string.cardview_conteudo_cruzeiro,false),
            new Pergunta(R.string.cardview_conteudo_gremio,false)
    };

    private View.OnClickListener mBotaoVerdadeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checaResposta(true);
            mIndiceAtual = (mIndiceAtual + 1) % mPerguntas.length;
            atualizaQuestao();
            revelaCard();
        }
    };

    private View.OnClickListener mBotaoFalsoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checaResposta(false);
            mIndiceAtual = (mIndiceAtual + 1) % mPerguntas.length;
            atualizaQuestao();
            revelaCard();
        }
    };

    private void atualizaQuestao() {
        int questao = mPerguntas[mIndiceAtual].getQuestao();
        mConteudoCard.setText(questao);
    }

    private void revelaCard() {
        //criando um reveal circular
        Animator animator = ViewAnimationUtils.createCircularReveal(
                mCardView,
                0,
                0,
                0,
                (float) Math.hypot(mCardView.getWidth(), mCardView.getHeight()));

        // interpolador ease-in/ease-out.
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static FutebolQuizFragment newInstance(String param1, String param2) {
        FutebolQuizFragment fragment = new FutebolQuizFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FutebolQuizFragment() {
        // Required empty public constructor
    }

    private void checaResposta(boolean botaoPressionado) {
        boolean resposta = mPerguntas[mIndiceAtual].isQuestaoVerdadeira();
        int recursoRespostaId = 0;
        AudioPlayer player = new AudioPlayer();

        if (botaoPressionado == resposta) {
            player.play(getActivity().getApplicationContext(), R.raw.cashregister);
            recursoRespostaId = R.string.toast_acertou;
        } else {
            player.play(getActivity().getApplicationContext(), R.raw.buzzer);
            recursoRespostaId = R.string.toast_errou;
        }

        Toast.makeText(getActivity(), recursoRespostaId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mIndiceAtual = savedInstanceState.getInt(KEY_INDICE,0);
        }

        mBotaoVerdade = (Button)getActivity().findViewById(R.id.botaoVerdade);
        mBotaoVerdade.setOnClickListener(mBotaoVerdadeListener);
        mBotaoFalso = (Button)getActivity().findViewById(R.id.botaoFalso);
        mBotaoFalso.setOnClickListener(mBotaoFalsoListener);

        mConteudoCard = (TextView)getActivity().findViewById(R.id.cardviewConteudo);
        atualizaQuestao();

        mCardView = (CardView)getActivity().findViewById(R.id.cardview);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDICE,mIndiceAtual);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean primeiraVez = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean(PREF_PRIMEIRA_VEZ,true);

        if (primeiraVez) {
            PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
                    .edit()
                    .putBoolean(PREF_PRIMEIRA_VEZ,false)
                    .commit();
            Toast.makeText(getActivity(), "Bem-vindo ao FutebolQuiz!", Toast.LENGTH_SHORT).show();
        }
    }
}