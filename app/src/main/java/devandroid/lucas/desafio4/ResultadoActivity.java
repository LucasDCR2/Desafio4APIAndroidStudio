package devandroid.lucas.desafio4;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Obtenha o JSON do quiz e a pontuação do intent
        String quizJson = getIntent().getStringExtra("quizJson");
        int score = getIntent().getIntExtra("score", 0);

        // ... processar os dados do quiz ou exibir outras informações relevantes ...

        // Exibir a pontuação para o usuário
        TextView textViewScore = findViewById(R.id.textViewScore);
        textViewScore.setText("Sua Pontuação: " + score + " de " + 4); // Substitua totalQuestoes pelo número total de questões no quiz
    }
}
