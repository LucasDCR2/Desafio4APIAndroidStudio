package devandroid.lucas.desafio4;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import devandroid.lucas.desafio4.getandset.Question;
import devandroid.lucas.desafio4.getandset.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private Quiz quiz;
    private List<Question> questoesRandom; // Lista de questões embaralhadas
    private int indiceQuestaoAtual = 0;
    private boolean questaoRespondida = false;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Recupere o JSON do quiz da Intent
        String quizJson = getIntent().getStringExtra("quizJson");

        // Crie um objeto Gson
        Gson gson = new Gson();

        // Converte a string JSON de volta para o objeto Quiz
        quiz = gson.fromJson(quizJson, Quiz.class);

        if (quiz != null) {
            // Crie uma cópia da lista original de questões
            questoesRandom = new ArrayList<>(quiz.getQuestions());
            // Embaralhe a lista de questões para reorganizá-las aleatoriamente
            Collections.shuffle(questoesRandom);

            // Exiba a primeira pergunta e opções de resposta
            mostrarQuestao();
        } else {
            // Se algo deu errado ao recuperar o objeto Quiz, trate o erro adequadamente.
            Log.e(TAG, "Erro ao recuperar o Quiz");
            finish();
        }

        // Configure o botão de "Próxima pergunta"
        findViewById(R.id.btnNextQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestao();
            }
        });
    }

    // Método para exibir a pergunta e opções de resposta na tela
    private void mostrarQuestao() {
        TextView textViewQuestao = findViewById(R.id.textViewQuestion);
        LinearLayout linearLayoutOpcoes = findViewById(R.id.linearLayoutOptions);

        // Obtenha a pergunta atual com base no índice atual da lista embaralhada
        Question questaoAtual = questoesRandom.get(indiceQuestaoAtual);

        textViewQuestao.setText(questaoAtual.getQuestion());
        linearLayoutOpcoes.removeAllViews();

        // Habilitar os botões de opção para a nova pergunta
        questaoRespondida = false;

        // Restaurar a cor original dos botões de opção para a nova pergunta
        for (int i = 0; i < linearLayoutOpcoes.getChildCount(); i++) {
            View childView = linearLayoutOpcoes.getChildAt(i);
            childView.setEnabled(true); // Habilitar o botão de opção
            childView.setBackgroundColor(ContextCompat.getColor(this, R.color.buttonBackground));
        }

        // Embaralhar as opções de resposta
        List<String> opcoesEmbaralhadas = new ArrayList<>(questaoAtual.getOptions());
        Collections.shuffle(opcoesEmbaralhadas);

        // Adicione as novas opções de resposta dinamicamente
        for (int i = 0; i < opcoesEmbaralhadas.size(); i++) {
            String opcao = opcoesEmbaralhadas.get(i);
            LinearLayout buttonOpcao = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.button_option_item, linearLayoutOpcoes, false);

            // Encontrar a TextView dentro do LinearLayout
            TextView textViewOpcao = buttonOpcao.findViewById(R.id.textViewOption);
            textViewOpcao.setText(opcao);
            buttonOpcao.setId(i);

            buttonOpcao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!questaoRespondida) {
                        // Obtenha o índice da opção
                        int selectedOptionIndex = v.getId();
                        String selectedOption = opcoesEmbaralhadas.get(selectedOptionIndex);

                        // Obtenha o índice da opção correta (right_option)
                        int correctOptionIndex = questaoAtual.getRightOption();
                        String correctOption = questaoAtual.getOptions().get(correctOptionIndex);
                        boolean isCorrect = selectedOption.equals(correctOption);

                        if (isCorrect) {
                            buttonOpcao.setBackgroundColor(ContextCompat.getColor(QuizActivity.this, R.color.green));
                            Log.d(TAG, "Resposta correta!");
                            score++;
                        } else {
                            buttonOpcao.setBackgroundColor(ContextCompat.getColor(QuizActivity.this, R.color.red));
                            Log.d(TAG, "Resposta incorreta!");
                        }

                        // Marque a pergunta como respondida
                        questaoRespondida = true;

                        desabilitarOpcoes(linearLayoutOpcoes, selectedOptionIndex);
                    }
                }
            });
            linearLayoutOpcoes.addView(buttonOpcao);
        }
    }

    // Método para desabilitar todas as opções, exceto a opção selecionada pelo usuário
    private void desabilitarOpcoes(LinearLayout layout, int indiceOpcaoSelecionada) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View childView = layout.getChildAt(i);
            childView.setEnabled(i == indiceOpcaoSelecionada);
        }
    }

    private void nextQuestao() {
        // Verifique se ainda há mais perguntas na lista embaralhada
        if (indiceQuestaoAtual + 1 < questoesRandom.size()) {
            indiceQuestaoAtual++;
            mostrarQuestao();
        } else {
            Intent intent = new Intent(this, ResultadoActivity.class);
            // Converta o objeto Quiz de volta para JSON usando o Gson
            Gson gson = new Gson();
            String quizJson = gson.toJson(quiz);

            // Passe o JSON do quiz para a atividade de resultado
            intent.putExtra("quizJson", quizJson);
            intent.putExtra("score", score);

            startActivity(intent);
            finish();
        }
    }
}