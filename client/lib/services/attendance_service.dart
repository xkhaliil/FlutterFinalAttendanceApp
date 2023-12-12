import 'dart:convert';

import 'package:client/models/attendance_model.dart';
import 'package:http/http.dart' as http;

Future<Attendance> createAttendance(Attendance attendance) async {
  print("this is first line");
  print(attendance.toJson());
  final uri = Uri.parse('http://10.0.2.2:8055/api/v1/attendances/create');

  try {
    final response = await http.post(
      uri,
      headers: {'Content-Type': 'application/json'},
      body: json.encode(attendance.toJson()),
    );

    if (response.statusCode == 200) {
      // Assuming status code 200 means success. Adjust according to your API spec.
      Attendance newAttendance = Attendance.fromJson(json.decode(response.body));
      print("this is second line");
      print(newAttendance.toJson());
      return newAttendance;
    } else {
      // Handle non-200 responses or add specific status code checks as per your API spec.
      print('Request failed with status: ${response.statusCode}.');
      throw Exception('Failed to create attendance'); 
    }
  } on Exception catch (e) {
    print('Caught exception: $e');
    rethrow; // Optionally rethrow the exception after logging it or handling it.
  }
  
}
Future<Map<String, double>> getTotalAttendanceHours(String studentId) async {
  print("C'est l'identifiant de l'étudiant : $studentId");
  final uri = Uri.parse('http://10.0.2.2:8055/api/v1/attendances/$studentId/total');
  final response = await http.get(uri);

  if (response.statusCode == 200) {
    final Map<String, dynamic> data = json.decode(response.body);

    // Convertir les données dynamiques en une map de String à double
    Map<String, double> subjectTotalHours = {};
    data.forEach((key, value) {
      subjectTotalHours[key] = value.toDouble();
    });

    return subjectTotalHours;
  } else {
    throw Exception('Échec du chargement des données d\'absence');
  }
}

Future<List<Attendance>> getAttendance(String studentId) async {
  final uri = Uri.parse('http://10.0.2.2:8055/api/v1/attendances/$studentId');
  final response = await http.get(uri);
  if (response.statusCode == 200) {
        return (json.decode(response.body) as List)
        .map((student) => Attendance.fromJson(student))
        .toList();
  } else {
    throw Exception('Failed to load attendance data');
  }


}
Future<double> getTotalHours(String studentId) async {
  print("this is student id $studentId");
  final uri = Uri.parse('http://10.0.2.2:8055/api/v1/attendances/$studentId/heuretotale');
  final response = await http.get(uri);

  if (response.statusCode == 200) {
    // Decode the JSON and convert it into a map of subject to total hours
    return double.parse(response.body);
  } else {
    throw Exception('Failed to load attendance data');
  }
}