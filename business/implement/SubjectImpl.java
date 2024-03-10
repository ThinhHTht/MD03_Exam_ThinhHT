package raExam.business.implement;

import raExam.business.design.IOData;
import raExam.business.entity.Subject;

import java.util.Scanner;
import java.util.regex.Pattern;

import static raExam.presentation.SchoolManagement.listSubjects;
import static raExam.presentation.SchoolManagement.scanner;

public class SubjectImpl extends Subject implements IOData {

    @Override
    public void inputData() {
        setSubjectId(inputSubjectId(scanner));
        setSubjectName(inputSubjectName(scanner));
    }

    public static String inputSubjectId(Scanner scanner){
        System.out.println("Hãy nhập mã môn học:");
        do {
            String idInput =  scanner.nextLine();
            if(listSubjects.stream().anyMatch(subject -> subject.getSubjectId().equals(idInput))){
                System.err.println("Mã môn học đã bị trùng, vui lòng nhập lại!");
            }else {
                if(Pattern.matches("MH\\d{3}$", idInput)){
                    return idInput;
                }else {
                    System.err.println("Mã môn học phải bắt đầu bằng MH và 3 ký tự số bất kỳ!");
                }
            }
        }while (true);
    }

    public static String inputSubjectName(Scanner scanner){
        System.out.println("Hãy nhập tên môn học:");
        do {
            String nameInput = scanner.nextLine();
            if(listSubjects.stream().anyMatch(subject -> subject.getSubjectName().equals(nameInput))){
                System.err.println("Tên môn học đã bị trùng, vui lòng nhập lại!");
            }else {
                if(nameInput.trim().isEmpty()){
                    System.err.println("Không được để trống tên môn học!");
                }else {
                    return nameInput;
                }
            }
        }while (true);
    }
    @Override
    public void displayData() {
        System.out.printf("Mã môn học: %s - Tên môn học: %s\n", this.getSubjectId(), this.getSubjectName());
    }

}
