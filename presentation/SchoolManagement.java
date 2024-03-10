package raExam.presentation;

import raExam.business.entity.Mark;
import raExam.business.implement.MarkImpl;
import raExam.business.implement.StudentImpl;
import raExam.business.implement.SubjectImpl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static ra.business.implement.StudentImpl.*;
import static raExam.business.implement.MarkImpl.inputPoint;
import static raExam.business.implement.MarkImpl.selectStudent;
import static raExam.business.implement.StudentImpl.inputPhone;
import static raExam.business.implement.StudentImpl.inputStudentName;
import static raExam.business.implement.SubjectImpl.inputSubjectName;

public class SchoolManagement {
    public static List<StudentImpl> listStudents = new ArrayList<>();
    public static List<SubjectImpl> listSubjects = new ArrayList<>();
    public static List<MarkImpl> listMarks = new ArrayList<>();

    public static Scanner scanner = new Scanner(System.in);
    static int choice;
    public static void main(String[] args) {
        do {
            System.out.println("***********SCHOOL-MANAGEMENT**************");
            System.out.println("1. Quản lý học sinh");
            System.out.println("2. Quản lý môn học");
            System.out.println("3. Quản lý điểm thi");
            System.out.println("4. Thoát");
            System.out.print("Lựa chọn của bạn là:");
            inputChoice(scanner);
            switch (choice){
                case 1:
                    displayStudentManagementMenu();
                    break;
                case 2:
                    displaySubjectManagementMenu();
                    break;
                case 3:
                    displayMarkManagementMenu();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng nhập từ 1-4");
            }

        }while (true);

    }

    public static int inputChoice(Scanner scanner){
        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                return choice;
            }catch (NumberFormatException ex){
                System.err.println("Vui lòng nhập lại bằng số nguyên!");
            }
        }while (true);
    }
    public static void displayStudentManagementMenu(){
        boolean isExit = true;
        do {
            System.out.println("**************STUDENT-MANAGEMENT*****************");
            System.out.println("1. Thêm mới học sinh");
            System.out.println("2. Hiển thị danh sách tất cả học sinh đã lưu trữ");
            System.out.println("3. Thay đổi thông tin học sinh");
            System.out.println("4. Xóa học sinh (kiểm tra xem nếu sinh viên có điểm thi thì không xóa được)");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn là:");
            inputChoice(scanner);
            switch (choice){
                case 1:
                    addStudents(scanner);
                    break;
                case 2:
                    displayListStudents();
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-5");
            }
        }while (isExit);
    }

    public static void addStudents(Scanner scanner){
        System.out.println("Hãy nhập số học sinh muốn thêm:");
        inputChoice(scanner);
        for (int i = 0; i < choice; i++) {
            StudentImpl newStudent = new StudentImpl();
            newStudent.inputData();
            listStudents.add(newStudent);
        }
    }

    public static void displayListStudents(){
        System.out.println("Danh sách học sinh:");
        listStudents.stream().forEach(student -> student.displayData());
    }
    public static void updateStudent(Scanner scanner){
        System.out.println("Hãy nhập mã của học sinh muốn update:");
        inputChoice(scanner);
        int indexUpdate = findStudentById(choice);
        if(indexUpdate >= 0){
            boolean isExit = true;
            do {
                System.out.println("Hãy chọn mục bạn muốn update:");
                System.out.println("1. Cập nhật tên học sinh");
                System.out.println("2. Cập nhật ngày sinh");
                System.out.println("3. Cập nhật địa chỉ");
                System.out.println("4. Cập nhật giới tính");
                System.out.println("5. Cập nhật số điện thoại");
                System.out.println("6. Thoát khỏi cập nhật");
                System.out.print("Lựa chọn của bạn là:");
                inputChoice(scanner);
                switch (choice){
                    case 1:
                        listStudents.get(indexUpdate).setStudentName(inputStudentName(scanner));
                        break;
                    case 2:
                        listStudents.get(indexUpdate).setBirthDay(inputBirthDay(scanner));
                        break;
                    case 3:
                        listStudents.get(indexUpdate).setAddress(StudentImpl.inputAddress(scanner));
                        break;
                    case 4:
                        listStudents.get(indexUpdate).setGender(!listStudents.get(indexUpdate).isGender());
                        System.out.println("Đã cập nhật xong giới tính!");
                        break;
                    case 5:
                        listStudents.get(indexUpdate).setPhone(inputPhone(scanner));
                        break;
                    case 6:
                        isExit = false;
                        break;
                    default:
                        System.err.println("Vui lòng nhập từ 1-6");
                }
            }while (isExit);
        }else {
            System.err.println("Học sinh này không tồn tại!");
        }

    }

    public static int findStudentById(int id){
        for (int i = 0; i < listStudents.size() ; i++) {
            if(listStudents.get(i).getStudentId() == id){
                return i;
            }
        } return -1;
    }

    public static void deleteStudent(Scanner scanner){
        System.out.println("Hãy nhập mã của học sinh muốn xóa:");
        inputChoice(scanner);
        int indexDelete = findStudentById(choice);
        if(indexDelete >= 0){
            if(listMarks.stream().anyMatch(mark -> mark.getStudent().getStudentId() == choice)){
                System.err.println("Sinh viên này đang có điểm nên không thể xóa!");
            }else {
                listStudents.remove(indexDelete);
                System.out.println("Đã xóa xong học sinh này!");
            }
        }else {
            System.err.println("Không tồn tại học sinh này!");
        }
    }

    public static void displaySubjectManagementMenu(){
        boolean isExit = true;
        do {
            System.out.println("*************SUBJECT-MANAGEMENT**************");
            System.out.println("1. Thêm mới môn học");
            System.out.println("2. Hiển thị danh sách môn học");
            System.out.println("3. Thay đổi thông tin môn học");
            System.out.println("4. Xóa môn học (kiểm tra xem nếu sinh viên có điểm thi thì không xóa được)");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn là");
            inputChoice(scanner);
            switch (choice){
                case 1:
                    addSubjects(scanner);
                    break;
                case 2:
                    displayListSubjects();
                    break;
                case 3:
                    updateSubject(scanner);
                    break;
                case 4:
                    deleteSubject(scanner);
                    break;
                case 5:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-5");
            }
        }while (isExit);
    }

    public static void addSubjects(Scanner scanner){
        System.out.println("Hãy nhập số lượng môn học muốn thêm:");
        inputChoice(scanner);
        for (int i = 0; i < choice; i++) {
            SubjectImpl newSubject = new SubjectImpl();
            newSubject.inputData();
            listSubjects.add(newSubject);
        }
    }

    public static void displayListSubjects(){
        listSubjects.stream().forEach(subject -> subject.displayData());
    }

    public static void updateSubject(Scanner scanner){
        System.out.println("Hãy nhập mã của môn học muốn update:");
        String subjectName = scanner.nextLine();
        int indexUpdate = findSubjectById(subjectName);
        if(indexUpdate >= 0){
            System.out.println("Tiến hành cập nhật tên môn học:");
            listSubjects.get(indexUpdate).setSubjectName(inputSubjectName(scanner));
        }else {
            System.err.println("Môn học này không tồn tại!");
        }

    }

    public static int findSubjectById(String id){
        for (int i = 0; i < listSubjects.size(); i++) {
            if(listSubjects.get(i).getSubjectId().equals(id)){
                return i;
            }
        } return -1;
    }

    public static void deleteSubject(Scanner scanner){
        System.out.println("Hãy nhập mã môn học muốn xóa:");
        String idDelete = scanner.nextLine();
        int indexDelete = findSubjectById(idDelete);
        if(indexDelete >= 0){
            if(listMarks.stream().anyMatch(mark -> mark.getSubject().getSubjectId().equals(idDelete))){
                System.err.println("Môn học này chứa điểm nên không thể xóa!");
            }else {
                listMarks.remove(indexDelete);
                System.out.println("Đã xóa xong môn học này!");
            }
        }else {
            System.err.println("Môn học này không tồn tại!");
        }
    }

    public static void displayMarkManagementMenu(){
        boolean isExit = true;
        do {
            System.out.println("**************MARK-MANAGEMENT***************");
            System.out.println("1. Thêm mới điểm thi cho 1 sinh viên");
            System.out.println("2. Hiển thị danh sách tất cả điểm thi theo thứ tự điểm tăng dần");
            System.out.println("3. Thay đổi điểm theo sinh viên");
            System.out.println("4. Xóa điểm thi của sinh viên");
            System.out.println("5.Hiển thị danh sách điểm thi theo mã môn học");
            System.out.println("6.Hiển thị đánh giá học lực của từng học sinh theo mã môn học (giả sử <5 là yếu , <=6.5 là trung" +
                    "bình, <= 8 là khá, <= 9 là giỏi, còn lại là xuất sắc).");
            System.out.println("7. Thoát");
            System.out.print("Lựa chọn của bạn là:");
            inputChoice(scanner);
            switch (choice){
                case 1:
                    addMarks(scanner);
                    break;
                case 2:
                    displayListMarksESC();
                    break;
                case 3:
                    updateMarkByStudent(scanner);
                    break;
                case 4:
                    deleteMarkByStudent(scanner);
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-7");
            }
        }while (isExit);
    }

    public static void addMarks(Scanner scanner){
        System.out.println("Hãy nhập mã sinh viên muốn thêm điểm:");
        StudentImpl selectedStudent = selectStudent(scanner);
        MarkImpl newMark = new MarkImpl();
        newMark.setStudent(selectedStudent);
        newMark.inputData();
        listMarks.add(newMark);
    }

    public static void displayListMarksESC(){
        listMarks.stream().sorted(Comparator.comparing(MarkImpl::getPoint)).forEach(mark -> mark.displayData());
    }
    public static void updateMarkByStudent(Scanner scanner){
        System.out.println("Hãy nhập mã của học sinh muốn update điểm:");
        inputChoice(scanner);
        List<MarkImpl> listMarkForUpdate = findMarkByStudentId(choice);
        if(!listMarkForUpdate.isEmpty()){
            System.out.println("Hãy chọn mã điểm muốn cập nhật:");
            listMarkForUpdate.stream().forEach(mark -> System.out.printf("ID: %d - Tên môn học: %s\n", mark.getMarkId(), mark.getSubject().getSubjectName()));
            boolean isExit = true;
            do {
                System.out.print("Lựa chọn của bạn là:");
                inputChoice(scanner);
                if(listMarkForUpdate.stream().anyMatch(mark -> mark.getMarkId() == choice)){
                    int indexUpdate = -1;
                    for (int i = 0; i < listMarkForUpdate.size(); i++) {
                        if(listMarkForUpdate.get(i).getMarkId() == choice){
                            indexUpdate = i;
                            break;
                        }
                    }
                    listMarkForUpdate.get(indexUpdate).setPoint(inputPoint(scanner));
                    System.out.println("Bạn có muốn cập nhật tiếp không?");
                    System.out.println("1. Có");
                    System.out.println("2. Không");
                    System.out.println("Lựa chọn của bạn là:");
                    inputChoice(scanner);
                    if(choice == 2){
                        isExit = false;
                    }
                } else {
                    System.err.println("Không tìm thấy môn học phù hợp, vui lòng nhập lại!");
                }

            }while (isExit);
        }else {
            System.err.println("Học sinh này chưa có điểm!");
        }
    }

    public static List<MarkImpl> findMarkByStudentId(int id){
        List<MarkImpl> listMarkForUpdate = new ArrayList<>();
        for (int i = 0; i < listMarks.size(); i++) {
            if(listMarks.get(i).getStudent().getStudentId() == id){
                listMarkForUpdate.add(listMarks.get(i));
            }
        } return listMarkForUpdate;
    }

    public static void deleteMarkByStudent(Scanner scanner){
        System.out.println("Hãy nhập mã của học sinh muốn xóa điểm:");
        inputChoice(scanner);
        List<MarkImpl> listMarkForDelete = findMarkByStudentId(choice);
        if(!listMarkForDelete.isEmpty()){
            System.out.println("Hãy chọn mã điểm muốn xóa:");
            listMarkForDelete.stream().forEach(mark -> System.out.printf("ID: %d - Tên môn học: %s\n", mark.getMarkId(), mark.getSubject().getSubjectName()));
            System.out.print("Lựa chọn của bạn là:");
            boolean isExit = true;
            do {
                inputChoice(scanner);
                if(listMarkForDelete.stream().anyMatch(mark -> mark.getMarkId() == choice)){
                    int indexDelete = -1;
                    for (int i = 0; i < listMarkForDelete.size(); i++) {
                        if(listMarkForDelete.get(i).getMarkId() == choice){
                            indexDelete = i;
                            break;
                        }
                    }
                    listMarkForDelete.remove(indexDelete);
                    System.out.println("Đã xóa xong!");
                } else {
                    System.err.println("Không tìm thấy môn học phù hợp, vui lòng nhập lại!");
                }

            }while (isExit);
        }else {
            System.err.println("Học sinh này chưa có điểm!");
        }
    }


}
