import 'package:client/models/student_model.dart';
import 'package:client/models/subject_model.dart';

class Attendance {
  String? id;
  Student? student;
  Subject? subject;
  String? date;
  double? numberOfHours;

  Attendance({
    this.id,
    this.student,
    this.subject,
    required this.date,
    required this.numberOfHours,
  });

  factory Attendance.fromJson(Map<String, dynamic> json) {
    return Attendance(
      id: json['id'],
      student: json['student'] != null
          ? Student.fromJson(json['student'])
          : null,
      subject: json['subject'] != null
          ? Subject.fromJson(json['subject'])
          : null,
      date: json['date'],
      numberOfHours: json['numberOfHours'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'student': student?.toJson(),
      'subject': subject?.toJson(),
      'date': date,
      'numberOfHours': numberOfHours,
    };
  }
}