package androidstudio.edbud.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAssignment extends AppCompatActivity implements View.OnClickListener {

    Button bAddAssignment;
    EditText etScore, etAssignmentID, etDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        bAddAssignment = (Button) findViewById(R.id.bAddAssignment);
        bAddAssignment.setOnClickListener(this);

        etAssignmentID = (EditText) findViewById(R.id.etAssignmentID);
        etScore = (EditText) findViewById(R.id.etScore);
        etDate = (EditText) findViewById(R.id.etDate);
    }

    @Override
    public void onClick(View view){
        /*
        String AssignmentID = AssignmentID.getText().toString();
        IndividualCourse.Assignment.add(AssignmentID);
        int score = Integer.parseInt(etScore.getText().toString());
        */
        startActivity(new Intent(this, IndividualCourse.class));


    }
}