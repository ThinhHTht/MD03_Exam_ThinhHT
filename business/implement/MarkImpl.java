package raExam.business.implement;

import raExam.business.design.IOData;
import raExam.business.entity.Mark;

import java.util.Scanner;

import static raExam.presentation.SchoolManagement.*;

public class MarkImpl extends Mark implements IOData {
    @Override
    public void inputData() {
        setMarkId(generateId());
        setSubject(selectSubject(scanner));
        setPoint(inputPoint(scanner));
    }
    public static int generateId(){
        if(listMarks.isEmpty()){
            return 1;
        }else {
            return listMarks.stream().mapToInt(Mark::getMarkId).max().orElse(0) + 1;
        }
    }

    public static StudentImpl selectStudent(Scanner scanner){
        if(listStudents.isEmpty()){
            System.out.println("Vì hiện tại chưa có học sinh nào nên mời bạn thêm mới học sinh trước:");
            addStudents(scanner);
            System.out.println("Tiếp theo hãy chọn 1 mã trong các học sinh sau:");
            listStudents.stream().forEach(student -> System.out.printf("%d - %s\n", student.getStudentId(), student.getStudentName()));
            System.out.print("Lựa chọn của bạn là:");
            do {
                try{
                    int studentIdInput = Integer.parseInt(scanner.nextLine());
                    if(listStudents.stream().anyMatch(student -> student.getStudentId() == studentIdInput)){
                        return listStudents.get(findStudentById(studentIdInput));
                    }else {
                        System.err.println("Không tồn tại học sinh này, vui lòng nhập lại!");
                    }

                }catch (NumberFormatException ex){
                    System.err.println("Vui lòng nhập bằng số nguyên!");
                }

            }while (true);

        }else {
            System.out.println("Hãy chọn 1 trong các học sinh sau:");
            listStudents.stream().forEach(student -> System.out.printf("%d - %s\n", student.getStudentId(), student.getStudentName()));
            System.out.print("Lựa chọn của bạn là:");
            do {
                try{
                    int studentIdInput = Integer.parseInt(scanner.nextLine());
                    if(listStudents.stream().anyMatch(student -> student.getStudentId() == studentIdInput)){
                        return listStudents.get(findStudentById(studentIdInput));
                    }else {
                        System.err.println("Không tồn tại học sinh này, vui lòng nhập lại!");
                    }
                }catch (NumberFormatException ex){
                    System.err.println("Vui lòng nhập bằng số nguyên!");
                }
            }while (true);
        }

    }

    public static SubjectImpl selectSubject(Scanner scanner){
        if(listSubjects.isEmpty()){
            System.out.println("Vì hiện tại không có môn học nào nên mời bạn thêm môn học trước:");
            addSubjects(scanner);
            System.out.println("Tiếp theo hãy chọn môn học bạn muốn nhập điểm:");
            listSubjects.stream().forEach(subject -> System.out.printf("%s - %s\n", subject.getSubjectId(), subject.getSubjectName()));
            System.out.print("Lựa chọn của bạn là:");
            do {
                String subjectIdInput = scanner.nextLine();
                if(listSubjects.stream().anyMatch(subject -> subject.getSubjectId().equals(subjectIdInput))){
                    return listSubjects.get(findSubjectById(subjectIdInput));
                }else {
                    System.err.println("Không tồn tại môn học này, vui lòng nhập lại!");
                }
            }while (true);
        }else {
            System.out.println("Hãy chọn môn học bạn muốn nhập điểm:");
            listSubjects.stream().forEach(subject -> System.out.printf("%s - %s\n", subject.getSubjectId(), subject.getSubjectName()));
            System.out.print("Lựa chọn của bạn là:");
            do {
                String subjectIdInput = scanner.nextLine();
                if(listSubjects.stream().anyMatch(subject -> subject.getSubjectId().equals(subjectIdInput))){
                    return listSubjects.get(findSubjectById(subjectIdInput));
                }else {
                    System.err.println("Không tồn tại môn học này, vui lòng nhập lại!");
                }
            }while (true);
        }
    }
    public static double inputPoint(Scanner scanner){
        System.out.println("Hãy nhập số điểm:");
        do {
            try{
                double pointInput = Double.parseDouble(scanner.nextLine());
                if(pointInput >= 0 && pointInput <=10){
                    return pointInput;
                }else {
                    System.err.println("Vui lòng nhập từ 0-10!");
                }
            }catch (NumberFormatException ex){
                System.err.println("Vui lòng nhập lại bằng số thực!");
            }

        }while (true);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã điểm: %d - Tên học sinh: %s - Tên môn học: %s - Điểm số: %.2f\n", this.getMarkId(), this.getStudent().getStudentName(), this.getSubject().getSubjectName(), this.getPoint());
    }
}
