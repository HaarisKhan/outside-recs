//
//  UserRecommendations.swift
//  Outside Recs
//
//  Created by Alex Kazorian on 7/23/16.
//  Copyright © 2016 AlexK. All rights reserved.
//

import UIKit
import Material
import SnapKit

class UserRecommendations: UITableViewController {
    
    var artistArray: [String: [AnyObject]] = [:]
    let lineupHashSet = NSHashTable(options: NSPointerFunctionsOptions.ObjectPointerPersonality)
    var recommendations = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "OUTSIDE RECS"
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(prepareTableView), name: "haveTopArtists", object: nil)
        getInfo()
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 0
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return 0
    }

    private func getInfo() {
        
        let userDefaults = NSUserDefaults.standardUserDefaults()
        
        if let sessionObj : AnyObject = userDefaults.objectForKey("Spotify Session") {
            let sessionObjData = sessionObj as! NSData
            
            let session = NSKeyedUnarchiver.unarchiveObjectWithData(sessionObjData) as! SPTSession
            getUserInfo(session)
            getTopArtists(session)
        }
        
    }
    
    private func getUserInfo(session: SPTSession!) {
        do {
            let currentUserHTTPRequest = try SPTUser.createRequestForCurrentUserWithAccessToken(session.accessToken)
            NSURLConnection.sendAsynchronousRequest(currentUserHTTPRequest, queue: NSOperationQueue(), completionHandler: {(response: NSURLResponse?, receivedData: NSData?, error: NSError?) -> Void in
                if error != nil {
                    print(error?.localizedDescription)
                }
                else{
                    do{
                        var err: NSError? = nil
                        let jsonResult : NSDictionary = try NSJSONSerialization.JSONObjectWithData(receivedData!, options: NSJSONReadingOptions.AllowFragments) as! NSDictionary
                        print(jsonResult.objectForKey("birthday"))
                        print(jsonResult.objectForKey("country"))
                        print(jsonResult.objectForKey("uri"))
                        print(jsonResult.objectForKey("email"))
                    }
                    catch{}
                }
            })
        }
        catch{}
    }
    
    private func getTopArtists(session: SPTSession!) {
        let apiURL = "https://api.spotify.com/v1/me/top/artists"
        let url = NSURL(string: apiURL)
        
        var urlRequest = NSMutableURLRequest(URL: url!) as NSMutableURLRequest
        let headersAuth = NSString(format: "Bearer %@", session.accessToken)
        urlRequest.setValue(headersAuth as String, forHTTPHeaderField: "Authorization")
        
        let queue = NSOperationQueue()
        NSURLConnection.sendAsynchronousRequest(urlRequest, queue: queue, completionHandler: {(response: NSURLResponse?, recievedData: NSData?, error: NSError?) -> Void in
            if error != nil {
                print(error?.localizedDescription)
            }
            else {
                do {
                    var tempDict: [String: [AnyObject]] = [:]
                    var err: NSError? = nil
                    let jsonResult : NSDictionary = try NSJSONSerialization.JSONObjectWithData(recievedData!, options: .AllowFragments) as! NSDictionary
                
                    if let items = jsonResult["items"] as? [[String: AnyObject]] {
                        for artist in items {
                            let id = artist["id"] as! String
                            tempDict[id] = [artist["genres"]!, artist["images"]!]
                        }
                    }
                    self.artistArray = tempDict
                    print(self.artistArray)
                    NSNotificationCenter.defaultCenter().postNotificationName("haveTopArtists", object: nil)
                }
                catch{}
            }
        })
    }
    
    func prepareTableView(sender: AnyObject) {
        
    }
    
    private func getRelatedArtists(id: String, session: SPTSession!) -> String {
        var artistToRecommend = String!()
        
        let apiURL = String(format: "https://api.spotify.com/v1/artists/%02d/related-artists", id)
        let url = NSURL(string: apiURL)
        
        var urlRequest = NSMutableURLRequest(URL: url!) as NSMutableURLRequest
        let headersAuth = NSString(format: "Bearer %@", session.accessToken)
        urlRequest.setValue(headersAuth as String, forHTTPHeaderField: "Authorization")
        
        let queue = NSOperationQueue()
        NSURLConnection.sendAsynchronousRequest(urlRequest, queue: queue, completionHandler: {(response: NSURLResponse?, recievedData: NSData?, error: NSError?) -> Void in
            if error != nil {
                print(error?.localizedDescription)
            }
            else {
                do {
                    var err: NSError? = nil
                    let jsonResult : NSDictionary = try NSJSONSerialization.JSONObjectWithData(recievedData!, options: .AllowFragments) as! NSDictionary
                    
                    if let artists = jsonResult["artist"] as? [[String: AnyObject]] {
                        for artist in artists {
                            if self.lineupHashSet.member(artist["name"] as! String) != nil {
                                artistToRecommend = artist["name"] as! String
                                return
                            }
                        }
                    }
                }
                catch{}
            }
        })
        return artistToRecommend
    }
    
    /*
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("reuseIdentifier", forIndexPath: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
