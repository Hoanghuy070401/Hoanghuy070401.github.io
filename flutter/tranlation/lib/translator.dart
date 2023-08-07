// ignore_for_file: avoid_print

import 'dart:io';

import 'package:avatar_glow/avatar_glow.dart';
import 'package:flutter/material.dart';
import 'package:translator/translator.dart';
import 'package:speech_to_text/speech_to_text.dart' as stt;

class TranslatorApp extends StatefulWidget {
  const TranslatorApp({super.key});

  @override
  State<TranslatorApp> createState() => _TranslatorAppState();
}

class _TranslatorAppState extends State<TranslatorApp> {
  late stt.SpeechToText _speech;
  bool _isListening = false;
  double _confidence = 1.0;
  List<String> languages = [
    'English',
    'Hindi',
    'Arabic	',
    'German',
    'Russian',
    'Spanish',
    'Urdu',
    'Japanese	',
    'Italian',
    'Vietnamese'
  ];
  List<String> languagescode = [
    'en',
    'hi',
    'ar',
    'de',
    'ru',
    'es',
    'ur',
    'ja',
    'it',
    'vi'
  ];
  final translator = GoogleTranslator();
  String from = 'vi';
  String to = 'en';
  String data = 'Hello teacher tuyen';
  String selectedvalue = 'Vietnamese';
  String selectedvalue2 = 'English';
  TextEditingController controller =
      TextEditingController(text: 'Chào thầy Tuyền nha');
  final formkey = GlobalKey<FormState>();
  bool isloading = false;

  translate() async {
    try {
      if (formkey.currentState!.validate()) {
        await translator
            .translate(controller.text, from: from, to: to)
            .then((value) {
          data = value.text;
          isloading = false;
          setState(() {});
          // print(value);
        });
      }
    } on SocketException catch (_) {
      isloading = true;
      SnackBar mysnackbar = const SnackBar(
        content: Text('Internet not Connected'),
        backgroundColor: Colors.red,
        duration: Duration(seconds: 5),
      );
      ScaffoldMessenger.of(context).showSnackBar(mysnackbar);
      setState(() {});
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _speech = stt.SpeechToText();
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    controller.dispose();
  }

  // @override
  // void initState() {
  //   super.initState();
  //   translate();
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        flexibleSpace: Container(
            decoration: const BoxDecoration(
          color: Colors.blue,
        )),
        title: const Text(
          'Translator App',
          style: TextStyle(
              color: Colors.white, fontWeight: FontWeight.bold, fontSize: 28),
        ),
      ),
      backgroundColor: const Color.fromARGB(255, 171, 232, 241),
      body: SingleChildScrollView(
          child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const SizedBox(
            height: 30, //canh lề trên của khung body text in & out
          ),
          Container(
            margin: const EdgeInsets.symmetric(horizontal: 20),
            decoration: BoxDecoration(
                color: Colors.grey.shade100,
                borderRadius: BorderRadius.circular(30)),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text('From'),
                const SizedBox(
                  width: 80,
                ),
                DropdownButton(
                  value: selectedvalue,
                  focusColor: Colors.transparent,
                  items: languages.map((lang) {
                    return DropdownMenuItem(
                      value: lang,
                      child: Text(lang),
                      onTap: () {
                        if (lang == languages[0]) {
                          from = languagescode[0];
                        } else if (lang == languages[1]) {
                          from = languagescode[1];
                        } else if (lang == languages[2]) {
                          from = languagescode[2];
                        } else if (lang == languages[3]) {
                          from = languagescode[3];
                        } else if (lang == languages[4]) {
                          from = languagescode[4];
                        } else if (lang == languages[5]) {
                          from = languagescode[5];
                        } else if (lang == languages[6]) {
                          from = languagescode[6];
                        } else if (lang == languages[7]) {
                          from = languagescode[7];
                        } else if (lang == languages[8]) {
                          from = languagescode[8];
                        } else if (lang == languages[9]) {
                          to = languagescode[9];
                        }
                        setState(() {
                          // print(lang);
                          // print(from);
                        });
                      },
                    );
                  }).toList(),
                  onChanged: (value) {
                    selectedvalue = value!;
                  },
                )
              ],
            ),
          ),
          Container(
            padding: const EdgeInsets.all(20),
            margin: const EdgeInsets.all(20),
            decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(10),
                border: Border.all(color: Colors.black)),
            child: Form(
              key: formkey,
              child: TextFormField(
                controller: controller,
                maxLines: null,
                minLines: null,
                validator: (value) {
                  if (value!.isEmpty) {
                    return 'Please enter some text';
                  }
                  return null;
                },
                textInputAction: TextInputAction.done,
                decoration: const InputDecoration(
                    enabledBorder: InputBorder.none,
                    border: InputBorder.none,
                    errorBorder: InputBorder.none,
                    errorStyle: TextStyle(color: Colors.black)),
                style: const TextStyle(
                    color: Colors.black,
                    fontWeight: FontWeight.bold,
                    fontSize: 17),
              ),
            ),
          ),
          Container(
            margin: const EdgeInsets.symmetric(horizontal: 20),
            //kích ỡ khung của to language
            decoration: BoxDecoration(
                color: Colors.grey.shade100,
                borderRadius: BorderRadius.circular(30)),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text('To'),
                const SizedBox(
                  width: 80,
                ),
                DropdownButton(
                  value: selectedvalue2,
                  focusColor: Colors.black,
                  items: languages.map((lang) {
                    return DropdownMenuItem(
                      value: lang,
                      child: Text(lang),
                      onTap: () {
                        if (lang == languages[0]) {
                          to = languagescode[0];
                        } else if (lang == languages[1]) {
                          to = languagescode[1];
                        } else if (lang == languages[2]) {
                          to = languagescode[2];
                        } else if (lang == languages[3]) {
                          to = languagescode[3];
                        } else if (lang == languages[4]) {
                          to = languagescode[4];
                        } else if (lang == languages[5]) {
                          to = languagescode[5];
                        } else if (lang == languages[6]) {
                          to = languagescode[6];
                        } else if (lang == languages[7]) {
                          to = languagescode[7];
                        } else if (lang == languages[8]) {
                          to = languagescode[8];
                        } else if (lang == languages[9]) {
                          to = languagescode[9];
                        }
                        setState(() {
                          print(lang);
                          print(from);
                        });
                      },
                    );
                  }).toList(),
                  onChanged: (value) {
                    selectedvalue2 = value!;
                  },
                )
              ],
            ),
          ),
          Container(
            padding: const EdgeInsets.all(20), //khung out text translate
            margin: const EdgeInsets.all(20),
            decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(10),
                border: Border.all(color: Colors.black)),
            child: Center(
              child: SelectableText(
                data,
                style: const TextStyle(
                    color: Colors.black87,
                    fontSize: 18,
                    fontWeight: FontWeight.bold),
              ),
            ),
          ),
          ElevatedButton(
            onPressed: translate,
            style: ButtonStyle(
              backgroundColor:
                  MaterialStateProperty.all(Colors.lightBlue.shade400),
              fixedSize: MaterialStateProperty.all(
                  const Size(250, 45)), //kích cỡ button translate
            ),
            child: isloading
                ? const SizedBox.square(
                    dimension: 20,
                    child: CircularProgressIndicator(color: Colors.white),
                  )
                : const Text('Translate',
                    style: TextStyle(color: Colors.white, fontSize: 18)),
          ),
          const SizedBox(
            height: 30,
          )
        ],
      )),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
      floatingActionButton: AvatarGlow(
        animate: _isListening,
        glowColor: Theme.of(context).primaryColor,
        endRadius: 75.0,
        duration: const Duration(microseconds: 2000),
        repeatPauseDuration: const Duration(microseconds: 100),
        repeat: true,
        child: FloatingActionButton(
          onPressed: _listen,
          child: Icon(_isListening ? Icons.mic : Icons.mic_none_sharp),
        ),
      ),
    );
  }

  void _listen() async {
    if (!_isListening) {
      bool available = await _speech.initialize(
        onStatus: (val) => print('onStatus: $val'),
        onError: (val) => print('onError: $val'),
      );
      if (available) {
        setState(() => _isListening = true);
        _speech.listen(
          onResult: (val) => setState(() {
            controller.text = val.recognizedWords ;
            if (val.hasConfidenceRating && val.confidence > 0) {
              _confidence = val.confidence;
            }
          }),
        );
      }
    } else {
      setState(() => _isListening = false);
      _speech.stop();
    }
  }
}
