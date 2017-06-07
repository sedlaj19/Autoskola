package cz.sedlaj19.autoskola.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.sedlaj19.autoskola.R;
import cz.sedlaj19.autoskola.domain.model.User;
import cz.sedlaj19.autoskola.domain.repository.Container;
import cz.sedlaj19.autoskola.presentation.presenters.InstructorStudentsPresenter;
import cz.sedlaj19.autoskola.presentation.ui.listeners.OnStudentClickListener;

/**
 * Created by Honza on 12. 8. 2016.
 */
public class StudentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnStudentClickListener{

    private List<User> students;
    private InstructorStudentsPresenter.View view;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.student_item_name)
        TextView studentName;
        @BindView(R.id.student_item_phone)
        TextView studentPhone;
        @BindView(R.id.student_item_email)
        TextView studentEmail;

        private OnStudentClickListener listener;

        public ViewHolder(View itemView, OnStudentClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        public void setup(User user){
            studentName.setText(user.getName() + " " + user.getSurname());
            studentPhone.setText(user.getPhone());
            studentEmail.setText(user.getEmail());
        }

        @Override
        public void onClick(View view) {
            listener.onStudentClick(getAdapterPosition());
        }
    }

    public StudentsAdapter(InstructorStudentsPresenter.View view){
        students = new ArrayList<>();
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.card_student_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = students.get(position);

        ((ViewHolder)holder).setup(user);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void addNewStudents(List<User> students){
        if(this.students != null && !this.students.isEmpty()){
            this.students.clear();
        }
        this.students = new ArrayList<>(students);
        notifyDataSetChanged();
    }

    @Override
    public void onStudentClick(int position) {
        view.onStudentClicked(this.students.get(position));
    }

    public User getStudent(int position){
        return this.students.get(position);
    }

    public void removeStudent(int position){
        this.students.remove(position);
        notifyItemRemoved(position);
    }

    public void addStudent(User student, int position){
        this.students.add(position, student);
        notifyItemInserted(position);
    }
}
