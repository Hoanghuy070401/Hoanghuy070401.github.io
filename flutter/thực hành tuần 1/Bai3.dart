import 'package:flutter/material.dart';
import 'dart:math';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('Change Background Color')),
        body: ColorChangeWidget(),
      ),
    );
  }
}

class ColorChangeWidget extends StatefulWidget {
  @override
  _ColorChangeWidgetState createState() => _ColorChangeWidgetState();
}

class _ColorChangeWidgetState extends State<ColorChangeWidget> {
  Color _backgroundColor = Colors.white; // Initial background color

  // Function to generate a random color
  Color _getRandomColor() {
    Random random = Random();
    return Color.fromARGB(
      255,
      random.nextInt(256),
      random.nextInt(256),
      random.nextInt(256),
    );
  }

  void _changeBackgroundColor() {
    setState(() {
      _backgroundColor = _getRandomColor();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            width: 200,
            height: 200,
            color: _backgroundColor,
          ),
          SizedBox(height: 20),
          ElevatedButton(
            onPressed: _changeBackgroundColor,
            child: Text('Change Color'),
          ),
        ],
      ),
    );
  }
}