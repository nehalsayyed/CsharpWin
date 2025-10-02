using System;
using System.Windows.Forms;

namespace MyWindowsApp
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MainForm());
        }
    }

    public class MainForm : Form
    {
        public MainForm()
        {
            Text = "My Custom Windows App";
            Width = 800;
            Height = 600;
        }
    }
}
