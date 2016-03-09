package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.Deserializers;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.Term;

/**
 * Created by LunaLu on 2/23/16.
 */
public class AddTerm extends AppCompatActivity implements View.OnClickListener {

    private Button bAddTermCourses, bAddTerm;
    private Button bA, bB, bC, bD,bF;
    private EditText etTermName, etTermCourseId, etTermCourseUnit;
    private ArrayList<Courses> coursesArrayList = new ArrayList<>();
    private ListView courseList;
    private FrameLayout layout_main;
    private PopupWindow popup;
    private AddTermListAdapter myAdapter;
    private double courseGpa=-1.0, gpa=4.0, unit = 0.0, letterUnit = 0.0;
    private String courseGrade;
    private int courseUnit = 0;
    private Switch gradeSwitch;
    private boolean letter = true;
    private TextView switchStatus, termUnit, termGpa;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        this.findViewById();


        layout_main.getForeground().setAlpha(0);
        myAdapter = new AddTermListAdapter(this, coursesArrayList,false);
        courseList.setAdapter(myAdapter);

    }

    /*
    *   find all views
     */
    private void findViewById(){
        layout_main = (FrameLayout) findViewById( R.id.addTerm);
        etTermName = (EditText) findViewById(R.id.etTermID);
        termUnit = (TextView) findViewById(R.id.add_term_termUnit);
        termGpa = (TextView) findViewById(R.id.add_term_termGpa);
        bAddTerm = (Button) findViewById(R.id.bAddTerm);
        bAddTerm.setOnClickListener(this);
        bAddTermCourses = (Button) findViewById(R.id.bAddTermCourses);
        bAddTermCourses.setOnClickListener(this);
        courseList = (ListView) findViewById(R.id.list_add_term_courses);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bAddTerm:
                String termId = etTermName.getText().toString();
                if(TextUtils.isEmpty(termId)){
                    Toast.makeText(this,"Please input term name",Toast.LENGTH_LONG).show();
                    return;
                }
                Term temp = new Term(termId, unit, gpa, coursesArrayList);
                BaseActivity.initialize.addPastTerm(temp);
                startActivity(new Intent(this, FourYearPlan.class));
                break;
            case R.id.bAddTermCourses:
                showPop(this);
                break;
            case R.id.bpopupAddTermCourse:
                String courseId = etTermCourseId.getText().toString();
                String courseUnitString = etTermCourseUnit.getText().toString();
                if(TextUtils.isEmpty(courseId)){
                    Toast.makeText(this,"Please input course name",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(courseUnitString)){
                    Toast.makeText(this,"Please input course unit",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(courseGpa == -1.0){
                    Toast.makeText(this, "Please choose course grade", Toast.LENGTH_LONG).show();
                    return;
                }
                courseUnit = Integer.parseInt(courseUnitString);
                coursesArrayList.add(new Courses(courseId, courseUnit, letter, courseGpa, courseGrade));

                if(letter){
                    gpa = (letterUnit * gpa + courseUnit*courseGpa)/(letterUnit + courseUnit);
                    letterUnit+=courseUnit;
                }
                unit += courseUnit;
                termUnit.setText(df.format(unit));
                termGpa.setText(df.format(gpa));
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                myAdapter.notifyDataSetChanged();
                letter = true;
                courseGpa = -1.0;
                break;
            case R.id.bpopupCancelAddTermCourse:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            case R.id.bA:
                courseGpa = 4.0;
                if(letter)
                    courseGrade = "A";
                else
                    courseGrade = "P";
                changeButtonColor("A");
                break;
            case R.id.bB:
                if(!letter) {
                    courseGpa = 0.0;
                    courseGrade= "NP";
                }
                else {
                    courseGpa = 3.0;
                    courseGrade = "B";
                }
                changeButtonColor("B");
                break;
            case R.id.bC:
                courseGpa = 2.0;
                courseGrade = "C";
                changeButtonColor("C");
                break;
            case R.id.bD:
                courseGpa = 1.0;
                courseGrade = "D";
                changeButtonColor("D");
                break;
            case R.id.bF:
                courseGpa = 0.0;
                courseGrade = "F";
                changeButtonColor("F");
                break;



        }
    }

    public void showPop(Activity context){
        // Inflate the popup_layout.xml
        // RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_add_termcourse, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etTermCourseId= (EditText) layout.findViewById(R.id.etTermCourseName);
        etTermCourseUnit = (EditText) layout.findViewById(R.id.etTermCourseUnit);
        bA = (Button) layout.findViewById(R.id.bA);
        bB = (Button) layout.findViewById(R.id.bB);
        bC = (Button) layout.findViewById(R.id.bC);
        bD = (Button) layout.findViewById(R.id.bD);
        bF = (Button) layout.findViewById(R.id.bF);
        bA.setOnClickListener(this);
        bB.setOnClickListener(this);
        bC.setOnClickListener(this);
        bD.setOnClickListener(this);
        bF.setOnClickListener(this);

        gradeSwitch = (Switch) layout.findViewById(R.id.letterSwitch);
        switchStatus = (TextView) layout.findViewById(R.id.letterSwitchStatus);

        switchStatus.setText("Letter grade");

        gradeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchStatus.setText("Pass/No Pass");
                    letter = false;
                    bA.setText("P");
                    bB.setText("NP");
                    bC.setVisibility(View.GONE);
                    bD.setVisibility(View.GONE);
                    bF.setVisibility(View.GONE);
                    courseGpa = -1.0;
                    changeButtonColor("Reset");

                } else {
                    switchStatus.setText("Letter grade");
                    letter = true;
                    bA.setText("A");
                    bB.setText("B");
                    bC.setVisibility(View.VISIBLE);
                    bD.setVisibility(View.VISIBLE);
                    bF.setVisibility(View.VISIBLE);
                    courseGpa = -1.0;
                    changeButtonColor("Reset");
                }
            }
        });



        //Dim the background
        layout_main.getForeground().setAlpha(220);


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bpopupAddTermCourse);
        Button cancel = (Button) layout.findViewById(R.id.bpopupCancelAddTermCourse);
        cancel.setOnClickListener(this);
        close.setOnClickListener(this);

    }
    private void changeButtonColor(String button){
        switch(button){
            case "A":
                bA.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "B":
                bB.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bA.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "C":
                bC.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "D":
                bD.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "F":
                bF.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                break;
            default:
                bF.setBackgroundColor(Color.TRANSPARENT);
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                break;

        }
    }
}
