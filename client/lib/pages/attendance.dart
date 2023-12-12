import 'package:client/models/attendance_model.dart';
import 'package:client/models/subject_model.dart';
import 'package:client/pages/class.dart';
import 'package:client/pages/department.dart';
import 'package:client/pages/home.dart';
import 'package:client/pages/sign_in.dart';
import 'package:client/pages/subject.dart';
import 'package:client/services/student_service.dart';
import 'package:client/services/subject_service.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';


import '../models/student_model.dart';
import '../services/attendance_service.dart';

class AttendancePage extends StatefulWidget {
  const AttendancePage({super.key});

  @override
  State<AttendancePage> createState() => _AttendancePageState();
  
}

class _AttendancePageState extends State<AttendancePage> {
  Future<List<Student>> _students = getAllStudents();
  Future<List<Subject>> _subjects = getAllSubjects();
  Subject? dropdownValue;
  List<Attendance> attendances = [];


  void fetchStudents() async {
    setState(() {
      _students = getAllStudents();
      _subjects = getAllSubjects();
    });

    List<Subject> subjects = await _subjects;
    setState(() {
      dropdownValue = subjects[0];
    });
  }

  final _formKey = GlobalKey<FormState>();

  final TextEditingController _dateController = TextEditingController();
  final TextEditingController _numberOfHoursController =
      TextEditingController();

  DateTime _selectedDate = DateTime.now();

    Future<void> selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: _selectedDate,
      firstDate: DateTime(1900),
      lastDate: DateTime.now(),
    );

    if (picked != null && picked != _selectedDate) {
      setState(() {
        _selectedDate = picked;
        _dateController.text =
            _selectedDate.toLocal().toString().split(' ')[0];
      });
    }
  }

void addAttendance(Attendance attendance) async {
  await createAttendance(attendance);
  fetchStudents();
}

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Absences',
          style: TextStyle(
            color: Color(0xFF090808),
            fontSize: 18,
          ),
        ),
        backgroundColor: Colors.grey,
        elevation: 0.0,
        centerTitle: true,
        leading: Builder(
          builder: (context) => IconButton(
            icon: const Icon(
              Icons.menu,
              color: Colors.black,
            ),
            onPressed: () => Scaffold.of(context).openDrawer(),
          ),
        ),
        actions: [
          Container(
            margin: const EdgeInsets.all(8.0),
            alignment: Alignment.center,
            child: IconButton(
              icon: const Icon(
                Icons.logout,
                color: Colors.black
              ),
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => SignInPage()));
              },
            ),
          ),
        ],
      ),
      drawer: Drawer(
        child: ListView(
          children: [
            const DrawerHeader(
              decoration: BoxDecoration(
                color: Color(0xFF004CFF),
              ),
              child: Text(
                'Menu',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
            ListTile(
              title: const Text('Manage students'),
              onTap: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => const HomePage()));
              },
            ),
            ListTile(
              title: const Text('Manage classes'),
              onTap: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => const ClassPage()));
              },
            ),
            ListTile(
              title: const Text('Manage departments'),
              onTap: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const DepartmentPage()));
              },
            ),
            ListTile(
              title: const Text('Manage subjects'),
              onTap: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const SubjectPage()));
              },
            ),
            ListTile(
              title: const Text('Manage attendances'),
              onTap: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const AttendancePage()));
              },
            ),
          ],
        ),
      ),
      body:

      SizedBox(
        height: double.infinity,
        width: double.infinity,
        child: Container(
          margin: const EdgeInsets.all(8.0),
          child: FutureBuilder<List<Student>>(
            future: _students,
            builder: (context, snapshot) {

              if (snapshot.hasData) {

                return ListView.builder(

                  itemCount: snapshot.data!.length,
                  itemBuilder: (context, index) {

                    return Card(
                      child: ListTile(
                        tileColor: Colors.grey,
                        onTap: () {
                          Student student = snapshot.data![index];

  showDialog(
  context: context,
  builder: (BuildContext context) {
    return AlertDialog(
      title: const Text('Add absence'),
      content: 
      Form(
        key: _formKey,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            TextFormField(
              controller: _dateController,
              decoration: const InputDecoration(
                labelText: 'Date',
              ),
              onTap: () {
                selectDate(context);
              },
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please enter date';
                }
                return null;
              },
            ),
            TextFormField(
              controller: _numberOfHoursController,
              decoration: const InputDecoration(
                labelText: 'Number of hours',
              ),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please enter number of hours';
                }
                return null;
              },
            ),
            DropdownButtonFormField<Subject>(
              value: dropdownValue,
              icon: const Icon(Icons.arrow_downward),
              decoration: const InputDecoration(
                labelText: 'Subject',
              ),
              onChanged: (Subject? newValue) {
                setState(() {
                  dropdownValue = newValue;
                });
              },
              items: snapshot.data![index].classRoom!.subjects!.map<DropdownMenuItem<Subject>>((Subject value) {
                return DropdownMenuItem<Subject>(
                  value: value,
                  child: Text(value.name),
                );
              }).toList(),
            ),
          ],
        ),
      ),  
      actions: <Widget>[
        TextButton(
          child: const Text('Cancel'),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
        TextButton(
          child: const Text('Add'),
          onPressed: () {
            Attendance attendance = Attendance(
              date: _dateController.text,
              numberOfHours: double.parse(_numberOfHoursController.text),
              student: student,
              subject: dropdownValue,
            );
            addAttendance(attendance);
            Navigator.of(context).pop();
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => const AttendancePage(),
              ),
            );
          },
        ),
      ],
    );
  },
);
},
                        title: Text('${snapshot.data![index].firstName} ${snapshot.data![index].lastName}'),
                        subtitle: Text(snapshot.data![index].classRoom!.name),
                        leading: const Icon(Icons.dangerous, color: Colors.red,size: 40,),
                        trailing: Row(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              Tooltip(
                                message: 'View Attendance Details',
                                child: IconButton(
                                  icon: const Icon(Icons.remove_red_eye, color: Colors.red),
                                  onPressed: () async {
                                    try {
                                      String? studentId = snapshot.data![index].id; // Ensure you have the correct student ID
                                      Map<String, double> totalHours = await getTotalAttendanceHours(studentId!);

                                      showDialog(
                                        context: context,
                                        builder: (BuildContext context) {
                                          return AlertDialog(
                                            title: Text('The total amount of hours for each subject'),
                                            content: FutureBuilder<Map<String, double>>(
                                              future: getTotalAttendanceHours(studentId),
                                              builder: (context, snapshot) {
                                                if (snapshot.connectionState == ConnectionState.waiting) {
                                                  return Center(child: CircularProgressIndicator());
                                                } else if (snapshot.hasError) {
                                                  return Text('Error: ${snapshot.error}');
                                                } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                                                  return Text('No absences');
                                                }

                                                List<Widget> attendanceWidgets = [];
                                                snapshot.data!.forEach((subject, hours) {
                                                  attendanceWidgets.add(
                                                    ListTile(
                                                      title: Text(subject),
                                                      subtitle: Text('--->: $hours hour'),
                                                    ),
                                                  );
                                                });
                                                return SingleChildScrollView(
                                                  child: Column(
                                                    mainAxisSize: MainAxisSize.min,
                                                    crossAxisAlignment: CrossAxisAlignment.start,
                                                    children: attendanceWidgets,
                                                  ),
                                                );
                                              },
                                            ),
                                            actions: <Widget>[
                                              TextButton(
                                                child: Text('Close absence list'),
                                                onPressed: () {
                                                  Navigator.of(context).pop();
                                                },
                                              ),
                                            ],
                                          );
                                        },
                                      );
                                    } catch (e) {
                                      print('Failed to load total attendance hours: $e');
                                    }
                                  },
                                ),
                              ),

                              Tooltip(
                                message: 'View absence total hours',
                                child: IconButton(
                                  icon: const Icon(Icons.remove_red_eye, color: Colors.yellowAccent),
                                  onPressed: () async {
                                    try {
                                      String? studentId = snapshot.data![index].id; // Ensure you have the correct student ID
                                      double totalHoursMap = await getTotalHours(studentId!);

                                      showDialog(
                                        context: context,
                                        builder: (BuildContext context) {
                                          List<Attendance> attendances = [];
                                          return AlertDialog(
                                            title: Text('Total Absence Hours: $totalHoursMap hours :('),
                                            content: FutureBuilder<List<Attendance>>(
                                              future: getAttendance(studentId),
                                              builder: (context, snapshot) {
                                                if (snapshot.hasData) {
                                                  attendances = snapshot.data!;
                                                  return SizedBox(
                                                    height: 200,
                                                    width: 200,
                                                    child: ListView.builder(
                                                      itemCount: attendances.length,
                                                      itemBuilder: (context, index) {
                                                        return ListTile(
                                                          title: Text(attendances[index].subject!.name),
                                                          subtitle: Text(DateFormat('yyyy-MM-dd').format(DateTime.parse(attendances[index].date!))),
                                                          trailing: Text(attendances[index].numberOfHours.toString()),
                                                        );
                                                      },
                                                    ),
                                                  );
                                                } else if (snapshot.hasError) {
                                                  return Text('${snapshot.error}');
                                                }
                                                return const CircularProgressIndicator();
                                              },
                                            ),
                                            actions: <Widget>[
                                              TextButton(
                                                child: const Text('Close'),
                                                onPressed: () {
                                                  Navigator.of(context).pop();
                                                },
                                              ),
                                            ],
                                          );
                                        },
                                      );
                                    } catch (e) {
                                      print('Failed to load total attendance hours: $e');
                                    }
                                  },
                                ),
                              ),

                            ],
                          ),




                      ),
                    );
                  },
                );
              } else if (snapshot.hasError) {
                return Text('${snapshot.error}');
              }
              return const CircularProgressIndicator();
            },
          ),
        ),
      ),
    );
  }
}