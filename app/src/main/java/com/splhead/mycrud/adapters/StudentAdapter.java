package com.splhead.mycrud.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.splhead.mycrud.databinding.StudentItemBinding;
import com.splhead.mycrud.models.Student;

public class StudentAdapter extends ListAdapter<Student, StudentAdapter.StudentHolder> {
    private OnItemClickListener listener;

    public StudentAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getCpf().equals(newItem.getCpf()) &&
                    oldItem.getPhone().equals(newItem.getPhone()) &&
                    oldItem.getCep().equals(newItem.getCep());
        }
    };

    public Student getStudentAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StudentItemBinding itemBinding = StudentItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StudentHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student currentStudent = getItem(position);
        holder.bind(currentStudent);
    }



    class StudentHolder extends RecyclerView.ViewHolder {
        private final StudentItemBinding studentItemBinding;

        public StudentHolder(StudentItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.studentItemBinding = itemBinding;

            itemBinding.getRoot().setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }

        public void bind(Student currentStudent) {
            studentItemBinding.tvName.setText(currentStudent.getName());
            studentItemBinding.tvCPF.setText(currentStudent.getCpf());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Student student);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
