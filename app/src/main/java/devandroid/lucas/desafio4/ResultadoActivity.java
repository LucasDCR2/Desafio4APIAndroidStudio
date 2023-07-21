package devandroid.lucas.desafio4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import devandroid.lucas.desafio4.getandset.Question;
import devandroid.lucas.desafio4.getandset.Quiz;

import java.util.List;

public class ResultadoActivity extends AppCompatActivity {

    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Recupere o objeto Quiz e a pontuação (score) da Intent
        String quizJson = getIntent().getStringExtra("quizJson");
        int score = getIntent().getIntExtra("score", 0);

        // Crie um objeto Gson para converter o JSON de volta para um objeto Quiz
        Gson gson = new Gson();
        Quiz quiz = gson.fromJson(quizJson, Quiz.class);

        if (quiz != null) {
            mostrarResumo(quiz.getQuestions(), score);
        } else {
            finish();
        }

        // botão de "Finalizar"
        btnFinish = findViewById(R.id.btnFinish2);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Coloque aqui o código que você deseja executar quando a imagem é clicada.
                // Por exemplo, você pode abrir outra atividade ou executar uma ação específica.
                // Se não precisar de nenhuma ação, pode deixar vazio.
            }
        });
    }

    private void mostrarResumo(List<Question> questions, int score) {
        LinearLayout linearLayoutResumo = findViewById(R.id.linearLayoutResumo2);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int selectedOptionIndex = question.getOptions().indexOf(question.getSelectedOption());
            int correctOptionIndex = question.getRightOption();

            // Crie um novo Button para exibir a alternativa selecionada pelo usuário e a alternativa correta
            Button buttonResumo = new Button(this);
            buttonResumo.setEnabled(false);
            buttonResumo.setBackgroundResource(R.drawable.custom_buttom_resumo);

            // Defina o texto do botão com base nas alternativas selecionada e correta
            String selectedOptionText = "Sua resposta: " + getAlternativaLetra(selectedOptionIndex);
            String correctOptionText = "Resposta correta: " + getAlternativaLetra(correctOptionIndex);

            // Adicione a informação "Correto" ou "Incorreto" acima da alternativa selecionada
            if (selectedOptionIndex == correctOptionIndex) {
                buttonResumo.setTextColor(getResources().getColor(R.color.green)); // Cor verde para "Correto"
                buttonResumo.setText("Correto\n" + selectedOptionText + "\n" + correctOptionText);
            } else {
                buttonResumo.setTextColor(getResources().getColor(R.color.red)); // Cor vermelha para "Incorreto"
                buttonResumo.setText("Incorreto\n" + selectedOptionText + "\n" + correctOptionText);
            }

            linearLayoutResumo.addView(buttonResumo);

            // Adicione um espaço entre cada resumo de pergunta
            TextView textViewSpace = new TextView(this);
            textViewSpace.setText(""); // Texto vazio para criar espaço
            linearLayoutResumo.addView(textViewSpace);
        }
    }

    private String getAlternativaLetra(int index) {
        // Este método retorna a letra da alternativa com base no índice (exemplo: 0 -> "A", 1 -> "B", ...)
        return Character.toString((char) (index + 'A'));
    }
}