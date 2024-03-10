package raExam.business.implement;

import raExam.business.design.IOData;
import raExam.business.entity.Student;
import raExam.presentation.SchoolManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import static raExam.presentation.SchoolManagement.listStudents;
import static raExam.presentation.SchoolManagement.scanner;

public class StudentImpl extends Student implements IOData {
    @Override
    public void inputData() {
        setStudentId(generateId());
        setStudentName(inputStudentName(scanner));
        setBirthDay(inputDate(scanner));
        setAddress(inputAddress(scanner));
        setGender(inputGender(scanner));
        setPhone(inputPhone(scanner));
    }
    public static int generateId(){
        if(listStudents.isEmpty()){
            return 1;
        }else{
            return listStudents.stream().mapToInt(Student::getStudentId).max().orElse(0) + 1;
        }
    }
    public static String inputStudentName(Scanner scanner){
        System.out.println("Hãy nhập tên học sinh:");
        do {
            String nameInput = scanner.nextLine();
            if(nameInput.trim().isEmpty()){
                System.err.println("Không được để trống tên học sinh!");
            } else {
                return nameInput;
            }
        }while (true);
    }

    public static Date inputDate(Scanner scanner) {
        System.out.println("Hãy nhập ngày sinh của học sinh:");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        do {
            try {
                Date dateInput = sdf.parse(scanner.nextLine());
                return dateInput;
            } catch (Exception ex) {
                System.err.println();
            }
        } while (true);
    }

    public static String inputAddress(Scanner scanner){
        System.out.println("Hãy nhập địa chỉ của học sinh:");
        do {
            String addressInput = scanner.nextLine();
            if(addressInput.trim().isEmpty()){
                System.err.println("Không được để trống địa chỉ!");
            }else {
                return addressInput;
            }
        }while (true);
    }

    public static boolean inputGender(Scanner scanner){
        System.out.println("Hãy nhập giới tính của học sinh:");
        do {
            String genderInput = scanner.nextLine();
            if(Pattern.matches("(true|false)", genderInput)){
                return Boolean.parseBoolean(genderInput);
            }else {
                System.err.println("Vui lòng nhập lại là true hoặc false!");
            }
        }while (true);
    }

    public static String inputPhone(Scanner scanner){
        System.out.println("Hãy nhập số điện thoại:");
        do {
            String phoneInput = scanner.nextLine();
            if(listStudents.stream().anyMatch(student -> student.getPhone().equals(phoneInput))){
                System.err.println("Số điện thoại đã trùng, vui lòng nhập lại!");
            }else {
                if(Pattern.matches("0\\d{9,10}$", phoneInput)){
                    return phoneInput;
                } else {
                    System.err.println("Số điện thoại phải bắt đầu từ số 0 và có 10 hoặc 11 số!");
                }
            }
        }while (true);
    }


    @Override
    public void displayData() {
        System.out.printf("Mã học sinh: %d - Tên học sinh: %s - Ngày sinh: %s\n", this.getStudentId(), this.getStudentName(), this.getBirthDay());
        System.out.printf("Địa chỉ: %s - Giới tính: %s - Số điện thoại: %s", this.getAddress(), this.isGender()? "Nam" : "Nữ", this.getPhone());
    }

}
